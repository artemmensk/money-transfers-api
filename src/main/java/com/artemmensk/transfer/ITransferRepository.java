package com.artemmensk.transfer;

import java.util.List;
import java.util.Optional;

public interface ITransferRepository {
    Transfer create(Integer amount, Long source, Long destination);
    Optional<Transfer> getTransfer(String uuid);
    List<Transfer> getTransfersForAccount(Long id);
    List<Transfer> getAllTransfers();
}
