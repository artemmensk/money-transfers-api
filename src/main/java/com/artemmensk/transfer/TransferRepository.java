package com.artemmensk.transfer;

import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class TransferRepository implements ITransferRepository {

    private final Map<String, Transfer> transfers = new HashMap<>();

    @Override
    public Transfer create(Integer amount, Long from, Long to) {
        final Transfer transfer = new Transfer(amount, from, to);
        transfers.put(transfer.getUuid(), transfer);
        return transfer;
    }

    @Override
    public Optional<Transfer> getTransfer(String uuid) {
        return Optional.ofNullable(transfers.get(uuid));
    }

    @Override
    public List<Transfer> getTransfersForAccount(Long id) {
        return transfers.values().stream().filter(t -> t.getFrom().equals(id) || t.getTo().equals(id)).collect(Collectors.toList());
    }

    @Override
    public List<Transfer> getAllTransfers() {
        return new ArrayList<>(transfers.values());
    }
}
