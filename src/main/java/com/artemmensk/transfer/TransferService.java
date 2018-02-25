package com.artemmensk.transfer;

import com.artemmensk.account.Account;
import com.artemmensk.account.IAccountRepository;
import com.artemmensk.exception.AccountNotFound;
import com.artemmensk.exception.NotEnoughBalance;
import com.google.inject.Inject;

public class TransferService implements ITransferService {

    private final IAccountRepository accountRepository;
    private final ITransferRepository transferRepository;

    @Inject
    public TransferService(IAccountRepository accountRepository, ITransferRepository transferRepository) {
        this.accountRepository = accountRepository;
        this.transferRepository = transferRepository;
    }

    @Override
    public Transfer transfer(Integer amount, Long fromId, Long toId) throws AccountNotFound, NotEnoughBalance {

        System.out.println(amount + " from " + fromId + " to " + toId);

        final Account fromAccount = accountRepository.findById(fromId).orElseThrow(() -> new AccountNotFound(fromId));
        final Account toAccount = accountRepository.findById(toId).orElseThrow(() -> new AccountNotFound(toId));

        final Account firstLock;
        final Account secondLock;

        if (fromAccount.getId() > toAccount.getId()) {
            firstLock = fromAccount;
            secondLock = toAccount;
        } else {
            firstLock = toAccount;
            secondLock = fromAccount;
        }

        synchronized (firstLock) {
            synchronized (secondLock) {
                if (fromAccount.getBalance() < amount) {
                    throw new NotEnoughBalance(fromAccount.getId());
                }
                fromAccount.setBalance(fromAccount.getBalance() - amount);
                toAccount.setBalance(toAccount.getBalance() + amount);
                return transferRepository.create(amount, fromAccount.getId(), toAccount.getId());
            }
        }
    }
}
