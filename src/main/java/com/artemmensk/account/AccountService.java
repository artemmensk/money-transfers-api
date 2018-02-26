package com.artemmensk.account;

import com.artemmensk.exception.AccountNotFound;
import com.artemmensk.exception.NegativeDeposit;
import com.google.inject.Inject;

public class AccountService implements IAccountService {

    private final IAccountRepository accountRepository;

    @Inject
    public AccountService(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account create() {
        return accountRepository.create();
    }

    @Override
    public void deposit(Integer amount, Long id) throws AccountNotFound, NegativeDeposit {
        if (amount <= 0) {
            throw new NegativeDeposit();
        }
        final Account account = accountRepository.findById(id).orElseThrow(() -> new AccountNotFound(id));
        synchronized (account) {
            account.setBalance(account.getBalance() + amount);
        }
    }

    @Override
    public Account findById(Long id) throws AccountNotFound {
        return accountRepository.findById(id).orElseThrow(() -> new AccountNotFound(id));
    }
}
