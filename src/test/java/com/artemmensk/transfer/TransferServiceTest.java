package com.artemmensk.transfer;

import com.artemmensk.account.Account;
import com.artemmensk.account.IAccountRepository;
import com.artemmensk.exception.AccountNotFound;
import com.artemmensk.exception.NotEnoughBalance;
import com.artemmensk.exception.TheSameAccount;
import com.artemmensk.exception.TransferNotFound;
import com.google.inject.Inject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Guice(modules = TransferModuleMock.class)
public class TransferServiceTest {

    private static final Integer AMOUNT_1 = 10;
    private static final Integer AMOUNT_2 = 300;
    private static final Integer BALANCE_1 = 90;
    private static final Integer BALANCE_2 = 40;
    private static final Long ACCOUNT_1_ID = 1L;
    private static final Long ACCOUNT_2_ID = 2L;
    private static final Transfer TRANSFER_1 = new Transfer(AMOUNT_1, ACCOUNT_1_ID, ACCOUNT_2_ID);
    private static final Transfer TRANSFER_2 = new Transfer(AMOUNT_2, ACCOUNT_2_ID, ACCOUNT_1_ID);
    private static final List<Transfer> TRANSFERS = Arrays.asList(TRANSFER_1, TRANSFER_2);

    private final ITransferService service;
    private final IAccountRepository accountRepository;
    private final ITransferRepository transferRepository;

    @Inject
    public TransferServiceTest(ITransferService service, IAccountRepository accountRepository, ITransferRepository transferRepository) {
        this.service = service;
        this.accountRepository = accountRepository;
        this.transferRepository = transferRepository;
    }

    @BeforeMethod
    private void beforeEachTest() {
        org.mockito.Mockito.reset(accountRepository, transferRepository);
    }

    @Test
    public void getTransfer() throws TransferNotFound {
        // given
        when(transferRepository.getTransfer(TRANSFER_1.getUuid())).thenReturn(Optional.ofNullable(TRANSFER_1));

        // when
        final Transfer transfer = service.getTransfer(TRANSFER_1.getUuid());

        // then
        Assert.assertEquals(transfer, TRANSFER_1);
    }

    @Test(expectedExceptions = TransferNotFound.class)
    public void nonExistingTransfer() throws TransferNotFound {
        // given
        when(transferRepository.getTransfer(TRANSFER_1.getUuid())).thenReturn(Optional.ofNullable(null));

        // when
        service.getTransfer(TRANSFER_1.getUuid());

        // then
        // throws exception
    }

    @Test
    public void getTransfersForAccount() throws TransferNotFound {
        // given
        when(transferRepository.getTransfersForAccount(ACCOUNT_1_ID)).thenReturn(TRANSFERS);

        // when
        final List<Transfer> transfers = service.getTransfersForAccount(ACCOUNT_1_ID);

        // then
        Assert.assertEquals(transfers.size(), 2);
        Assert.assertTrue(transfers.containsAll(TRANSFERS));
    }

    @Test(expectedExceptions = TransferNotFound.class)
    public void nonExistingTransfersForAccount() throws TransferNotFound {
        // given
        when(transferRepository.getTransfersForAccount(ACCOUNT_1_ID)).thenReturn(new ArrayList<>());

        // when
        service.getTransfersForAccount(ACCOUNT_1_ID);

        // then
        // throws exception
    }

    @Test
    public void getAllTransfers() throws TransferNotFound {
        // given
        when(transferRepository.getAllTransfers()).thenReturn(Arrays.asList(TRANSFER_1, TRANSFER_2));

        // when
        final List<Transfer> transfers = service.getAllTransfers();

        // then
        Assert.assertEquals(transfers.size(), 2);
        Assert.assertTrue(transfers.containsAll(TRANSFERS));
    }

    @Test(expectedExceptions = TransferNotFound.class)
    public void nonExistingTransfers() throws TransferNotFound {
        // given
        when(transferRepository.getAllTransfers()).thenReturn(new ArrayList<>());

        // when
        service.getAllTransfers();

        // then
        // throws exception
    }

    @Test(expectedExceptions = AccountNotFound.class)
    public void sourceNotFound() throws AccountNotFound, NotEnoughBalance, TheSameAccount {
        // given
        when(accountRepository.findById(ACCOUNT_1_ID)).thenReturn(Optional.ofNullable(null));

        // when
        service.performTransfer(AMOUNT_1, ACCOUNT_1_ID, ACCOUNT_2_ID);

        // then
        // throws exception
    }

    @Test(expectedExceptions = AccountNotFound.class)
    public void destinationNotFound() throws AccountNotFound, NotEnoughBalance, TheSameAccount {
        // given
        when(accountRepository.findById(ACCOUNT_1_ID)).thenReturn(Optional.ofNullable(new Account()));
        when(accountRepository.findById(ACCOUNT_2_ID)).thenReturn(Optional.ofNullable(null));

        // when
        service.performTransfer(AMOUNT_1, ACCOUNT_1_ID, ACCOUNT_2_ID);

        // then
        // throws exception
    }

    @Test
    public void performTransfer() throws AccountNotFound, NotEnoughBalance, TheSameAccount {
        // given
        final Account account1 = new Account();
        final Account account2 = new Account();
        account1.setBalance(BALANCE_1);
        account2.setBalance(BALANCE_2);

        when(accountRepository.findById(account1.getId())).thenReturn(Optional.ofNullable(account1));
        when(accountRepository.findById(account2.getId())).thenReturn(Optional.ofNullable(account2));

        // when
        service.performTransfer(AMOUNT_1, account1.getId(), account2.getId());

        // then
        Assert.assertEquals(account1.getBalance(), new Integer(BALANCE_1 - AMOUNT_1));
        Assert.assertEquals(account2.getBalance(), new Integer(BALANCE_2 + AMOUNT_1));
        verify(transferRepository).create(AMOUNT_1, account1.getId(), account2.getId());
    }

    @Test(expectedExceptions = NotEnoughBalance.class)
    public void notEnoughBalance() throws AccountNotFound, NotEnoughBalance, TheSameAccount {
        // given
        final Account account1 = new Account();
        final Account account2 = new Account();
        account1.setBalance(BALANCE_1);
        account2.setBalance(BALANCE_2);

        when(accountRepository.findById(account1.getId())).thenReturn(Optional.ofNullable(account1));
        when(accountRepository.findById(account2.getId())).thenReturn(Optional.ofNullable(account2));

        // when
        service.performTransfer(AMOUNT_2, account1.getId(), account2.getId());

        // then
        // throws exception
    }

    @Test(expectedExceptions = TheSameAccount.class)
    public void theSameAccount() throws AccountNotFound, NotEnoughBalance, TheSameAccount {
        // given
        final Account account1 = new Account();

        when(accountRepository.findById(account1.getId())).thenReturn(Optional.ofNullable(account1));

        // when
        service.performTransfer(AMOUNT_2, account1.getId(), account1.getId());

        // then
        // throws exception
    }
}
