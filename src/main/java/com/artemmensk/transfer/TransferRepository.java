package com.artemmensk.transfer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TransferRepository implements ITransferRepository {

    private final Map<String, Transfer> transfers = new HashMap<>();

    @Override
    public Transfer create(Long from, Long to, Integer amount) {
        final Transfer transfer = new Transfer(from, to, amount);
        return transfers.put(transfer.getUuid(), transfer);
    }
}
