package com.artemmensk.transfer;

import java.util.List;
import java.util.Optional;

/**
 * Repository of transfer entities.
 */
public interface ITransferRepository {
    /**
     * Creates and returns new transfer using specific amount, source id and destination id.
     *
     * @param amount amount of money
     * @param source source identifier
     * @param destination destination identifier
     * @return  new transfer with specific amount, source id and destination id
     */
    Transfer create(Integer amount, Long source, Long destination);

    /**
     * Finds and returns Optional with transfer entity or Optional with empty using specific UUID
     *
     * @param uuid transfer UUID
     * @return Optional with transfer entity or Optional with empty
     */
    Optional<Transfer> getTransfer(String uuid);

    /**
     * Finds and returns list of transfers for specific account id. Account could be as source ans as destination.
     *
     * @param id account identifier
     * @return transfers for specific account id
     */
    List<Transfer> getTransfersForAccount(Long id);

    /**
     * Finds and returns list of all transfers from repository.
     *
      * @return all transfers
     */
    List<Transfer> getAllTransfers();
}
