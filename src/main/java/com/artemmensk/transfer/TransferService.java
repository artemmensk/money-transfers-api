package com.artemmensk.transfer;

import com.artemmensk.account.Account;
import com.artemmensk.account.IAccountRepository;
import com.artemmensk.exception.AccountNotFound;
import com.artemmensk.exception.NotEnoughBalance;
import com.artemmensk.exception.TheSameAccount;
import com.artemmensk.exception.TransferNotFound;
import com.google.inject.Inject;

import java.util.List;

public class TransferService implements ITransferService {

    private final IAccountRepository accountRepository;
    private final ITransferRepository transferRepository;

    @Inject
    public TransferService(IAccountRepository accountRepository, ITransferRepository transferRepository) {
        this.accountRepository = accountRepository;
        this.transferRepository = transferRepository;
    }

    @Override
    public Transfer performTransfer(Integer amount, Long sourceId, Long destinationId) throws AccountNotFound, NotEnoughBalance, TheSameAccount {

        if (sourceId.equals(destinationId)) {
            throw new TheSameAccount();
        }

        final Account sourceAccount = accountRepository.findById(sourceId).orElseThrow(() -> new AccountNotFound(sourceId));
        final Account destinationAccount = accountRepository.findById(destinationId).orElseThrow(() -> new AccountNotFound(destinationId));

        final Account firstLock;
        final Account secondLock;

        if (sourceAccount.getId() > destinationAccount.getId()) {
            firstLock = sourceAccount;
            secondLock = destinationAccount;
        } else {
            firstLock = destinationAccount;
            secondLock = sourceAccount;
        }

        synchronized (firstLock) {
            synchronized (secondLock) {
                final Integer balance = sourceAccount.getBalance();
                if (balance < amount) {
                    throw new NotEnoughBalance(balance);
                }
                sourceAccount.setBalance(sourceAccount.getBalance() - amount);
                destinationAccount.setBalance(destinationAccount.getBalance() + amount);
                return transferRepository.create(amount, sourceAccount.getId(), destinationAccount.getId());
            }
        }
    }

    @Override
    public Transfer getTransfer(String uuid) throws TransferNotFound {
        return transferRepository.getTransfer(uuid).orElseThrow(() -> new TransferNotFound(uuid));
    }

    @Override
    public List<Transfer> getTransfersForAccount(Long id) throws TransferNotFound {
        final List<Transfer> transfers = transferRepository.getTransfersForAccount(id);

        if (transfers.isEmpty()) {
            throw new TransferNotFound(id);
        }

        return transfers;
    }

    @Override
    public List<Transfer> getAllTransfers() throws TransferNotFound {
        final List<Transfer> transfers = transferRepository.getAllTransfers();

        if (transfers.isEmpty()) {
            throw new TransferNotFound();
        }

        return transfers;
    }
}
