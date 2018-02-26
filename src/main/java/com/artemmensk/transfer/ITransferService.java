package com.artemmensk.transfer;

import com.artemmensk.exception.AccountNotFound;
import com.artemmensk.exception.NotEnoughBalance;
import com.artemmensk.exception.TheSameAccount;
import com.artemmensk.exception.TransferNotFound;

import java.util.List;

/**
 * Service for manage transfers.
 */
public interface ITransferService {

    /**
     * Performs wire transfer between two accounts.
     * Changes balances for source and destination accounts.
     * Create and returns transfer entity.
     *
     * @param amount money amount of wire transfer
     * @param sourceId identifier of source account
     * @param destinationId identifier of destination account
     * @return transfer entity with information about wire transfer
     * @throws AccountNotFound  if source or destination account was not found
     * @throws NotEnoughBalance if balance of source account is not enough for wire transfer
     * @throws TheSameAccount if source and destination accounts are the same
     */
    Transfer performTransfer(Integer amount, Long sourceId, Long destinationId) throws AccountNotFound, NotEnoughBalance, TheSameAccount;

    /**
     * Finds and returns transfer entity for specific UUID.
     *
     * @param uuid transfer uuid
     * @return transfer entity with specific UUID
     * @throws TransferNotFound if transfer with specific UUID not found
     */
    Transfer getTransfer(String uuid) throws TransferNotFound;

    /**
     * Finds and returns list of transfers for specific account id. Account could be as source ans as destination.
     *
     * @param id account identifier
     * @return list of transfers for specific account id
     * @throws TransferNotFound if any was found
     */
    List<Transfer> getTransfersForAccount(Long id) throws TransferNotFound;

    /**
     * Finds and returns list of all transfers from repository.
     *
     * @return list of all transfers
     * @throws TransferNotFound if any was found
     */
    List<Transfer> getAllTransfers() throws TransferNotFound;
}
