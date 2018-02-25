package com.artemmensk.transfer;

import com.artemmensk.account.AccountModule;
import com.google.inject.Inject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import java.sql.Timestamp;
import java.util.*;

@Guice(modules = {TransferModule.class, AccountModule.class})
public class TransferRepositoryTest {

    private static final Long ACCOUNT_1_ID = 1L;
    private static final Long ACCOUNT_2_ID = 2L;
    private static final Long ACCOUNT_3_ID = 3L;

    private static final Integer AMOUNT_1 = 500;
    private static final Integer AMOUNT_2 = 750;
    private static final Integer AMOUNT_3 = 1999;

    private static final String NON_EXISTING_TRANSFER_UUID = "9eef47a5-f590-428a-9b55-c258f521e60a";

    private static final Transfer TRANSFER_FROM_1_TO_2 = new Transfer(AMOUNT_1, ACCOUNT_1_ID, ACCOUNT_2_ID);
    private static final Transfer TRANSFER_FROM_2_TO_1 = new Transfer(AMOUNT_2, ACCOUNT_2_ID, ACCOUNT_1_ID);
    private static final Transfer TRANSFER_FROM_2_TO_3 = new Transfer(AMOUNT_3, ACCOUNT_2_ID, ACCOUNT_3_ID);

    private ITransferRepository repository;
    private Map<String, Transfer> transfers;

    @Inject
    public TransferRepositoryTest(ITransferRepository repository) {
        this.repository = repository;
        transfers = ((TransferRepository)repository).getTransfers();
    }

    @BeforeMethod
    public void beforeEachTest() {
        transfers.clear();
    }

    @Test
    public void create() {
        // when
        final Timestamp now = new Timestamp(new Date().getTime());
        final Transfer transfer = repository.create(AMOUNT_1, ACCOUNT_1_ID, ACCOUNT_2_ID);

        // then
        Assert.assertEquals(transfers.size(), 1);
        Assert.assertEquals(transfer, transfers.get(transfer.getUuid()));
        Assert.assertTrue(transfer.getTimestamp().getTime() >= now.getTime());
        Assert.assertTrue(transfer.getTimestamp().getTime() < now.getTime() + 1000);
    }

    @Test
    public void getExistingTransfer() {
        // given
        final Transfer existingTransfer = new Transfer(AMOUNT_1, ACCOUNT_1_ID, ACCOUNT_2_ID);
        ((TransferRepository)repository).getTransfers().put(existingTransfer.getUuid(), existingTransfer);

        // when
        final Transfer transfer = repository.getTransfer(existingTransfer.getUuid()).get();

        // then
        Assert.assertEquals(transfer, existingTransfer);
    }

    @Test
    public void getNonExistingTransfer() {
        // when
        final Optional<Transfer> transfer = repository.getTransfer(NON_EXISTING_TRANSFER_UUID);

        // then
        Assert.assertFalse(transfer.isPresent());
    }

    @Test
    public void getTransfersForAccount() {
        // given
        transfers.put(TRANSFER_FROM_1_TO_2.getUuid(), TRANSFER_FROM_1_TO_2);
        transfers.put(TRANSFER_FROM_2_TO_1.getUuid(), TRANSFER_FROM_2_TO_1);
        transfers.put(TRANSFER_FROM_2_TO_3.getUuid(), TRANSFER_FROM_2_TO_3);

        // when
        final List<Transfer> transfersForAccount1 = this.repository.getTransfersForAccount(ACCOUNT_1_ID);

        // then
        Assert.assertEquals(transfersForAccount1.size(), 2);
        Assert.assertTrue(transfersForAccount1.containsAll(Arrays.asList(TRANSFER_FROM_1_TO_2, TRANSFER_FROM_2_TO_1)));
    }

    @Test
    public void getAllTransfers() {
        // given
        transfers.put(TRANSFER_FROM_1_TO_2.getUuid(), TRANSFER_FROM_1_TO_2);
        transfers.put(TRANSFER_FROM_2_TO_1.getUuid(), TRANSFER_FROM_2_TO_1);
        transfers.put(TRANSFER_FROM_2_TO_3.getUuid(), TRANSFER_FROM_2_TO_3);

        // when
        final List<Transfer> transfers = this.repository.getAllTransfers();

        // then
        Assert.assertEquals(transfers.size(), 3);
        Assert.assertTrue(transfers.containsAll(Arrays.asList(TRANSFER_FROM_1_TO_2, TRANSFER_FROM_2_TO_1)));
    }
}
