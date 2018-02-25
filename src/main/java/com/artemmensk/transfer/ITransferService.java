package com.artemmensk.transfer;

import com.artemmensk.exception.AccountNotFound;
import com.artemmensk.exception.NotEnoughBalance;
import com.artemmensk.exception.TheSameAccount;
import com.artemmensk.exception.TransferNotFound;

import java.util.List;

public interface ITransferService {
    Transfer performTransfer(Integer amount, Long sourceId, Long destinationId) throws AccountNotFound, NotEnoughBalance, TheSameAccount;
    Transfer getTransfer(String uuid) throws TransferNotFound;
    List<Transfer> getTransfersForAccount(Long id) throws TransferNotFound;
    List<Transfer> getAllTransfers() throws TransferNotFound;
}
