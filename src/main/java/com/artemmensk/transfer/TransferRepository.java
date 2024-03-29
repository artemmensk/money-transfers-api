package com.artemmensk.transfer;

import com.google.inject.Inject;

import javax.inject.Named;
import java.util.*;
import java.util.stream.Collectors;

public class TransferRepository implements ITransferRepository {

    private final Map<String, Transfer> transfers;

    @Inject
    public TransferRepository(@Named("transfers") Map<String, Transfer> transfers) {
        this.transfers = transfers;
    }

    @Override
    public Transfer create(Integer amount, Long source, Long destination) {
        final Transfer transfer = new Transfer(amount, source, destination);
        transfers.put(transfer.getUuid(), transfer);
        return transfer;
    }

    @Override
    public Optional<Transfer> getTransfer(String uuid) {
        return Optional.ofNullable(transfers.get(uuid));
    }

    @Override
    public List<Transfer> getTransfersForAccount(Long id) {
        return transfers.values().stream().filter(t -> t.getSource().equals(id) || t.getDestination().equals(id)).collect(Collectors.toList());
    }

    @Override
    public List<Transfer> getAllTransfers() {
        return new ArrayList<>(transfers.values());
    }
}
