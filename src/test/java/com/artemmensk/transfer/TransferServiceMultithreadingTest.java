package com.artemmensk.transfer;

import com.artemmensk.account.Account;
import com.artemmensk.account.IAccountRepository;
import com.artemmensk.exception.AccountNotFound;
import com.artemmensk.exception.NotEnoughBalance;
import com.artemmensk.exception.TheSameAccount;
import com.google.inject.Inject;
import org.testng.TestException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

import static org.mockito.Mockito.when;

/**
 * Multithreading tests fot {@link ITransferService}
 *
 * @author artemmensk@gmail.com
 */
@Guice(modules = MockedTransferModule.class)
public class TransferServiceMultithreadingTest {

    private static final int THREADS_AMOUNT = 1000;
    private static final CyclicBarrier GATE = new CyclicBarrier(THREADS_AMOUNT + 1);

    private static final Integer AMOUNT = 10;
    private static final Integer BALANCE_1 = 1000;
    private static final Integer BALANCE_2 = 2000;

    private static final Account ACCOUNT_1 = new Account();
    private static final Account ACCOUNT_2 = new Account();

    static {
        ACCOUNT_1.setBalance(BALANCE_1);
        ACCOUNT_2.setBalance(BALANCE_2);
    }

    private final ITransferService service;
    private final IAccountRepository accountRepository;
    private final ITransferRepository transferRepository;

    @BeforeMethod
    private void beforeEachTest() {
        org.mockito.Mockito.reset(accountRepository, transferRepository);
    }

    @Inject
    public TransferServiceMultithreadingTest(ITransferService service, IAccountRepository accountRepository, ITransferRepository transferRepository) {
        this.service = service;
        this.accountRepository = accountRepository;
        this.transferRepository = transferRepository;
    }

    /**
     * For dead lock detection:
     * 1) Starts many threads "the same" time using {@link CyclicBarrier}.
     * 2) Even threads perform transfer in opposite direction.
     * 3) Wait until threads done otherwise timeout.
     */
    @Test
    public void deadLockTest() throws BrokenBarrierException, InterruptedException, ExecutionException {
        // given
        when(accountRepository.findById(ACCOUNT_1.getId())).thenReturn(Optional.ofNullable(ACCOUNT_1));
        when(accountRepository.findById(ACCOUNT_2.getId())).thenReturn(Optional.ofNullable(ACCOUNT_2));

        final ExecutorService executorService = Executors.newFixedThreadPool(THREADS_AMOUNT);
        final List<Future<Runnable>> futures = new ArrayList<>();

        // when
        for (int i = 0; i < THREADS_AMOUNT; i++) {
            final Future future = executorService.submit(new Worker(i % 2 == 0));
            futures.add(future);
        }

        // start threads "the same" moment
        GATE.await();

        // wait until all threads done or timeout
        for (Future<Runnable> f : futures) {
            try {
                f.get(5, TimeUnit.SECONDS);
            } catch (TimeoutException e) {
                throw new TestException("during TransferService.performTransfer() dead lock occurred", e);
            }
        }

        // then
        // test will success
    }

    class Worker implements Runnable {

        final boolean opposite;

        Worker(boolean opposite) {
            this.opposite = opposite;
        }

        @Override
        public void run() {
            try {

                GATE.await();

                if (opposite) {
                    service.performTransfer(AMOUNT, ACCOUNT_2.getId(), ACCOUNT_1.getId());
                } else {
                    service.performTransfer(AMOUNT, ACCOUNT_1.getId(), ACCOUNT_2.getId());
                }

            } catch (AccountNotFound | NotEnoughBalance | TheSameAccount | InterruptedException | BrokenBarrierException ex) {
                ex.printStackTrace();
            }
        }
    }
}
