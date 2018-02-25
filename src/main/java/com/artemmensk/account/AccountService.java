package com.artemmensk.account;

import com.artemmensk.exception.AccountNotFound;
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
    public Account findById(Long id) throws AccountNotFound {
        return accountRepository.findById(id).orElseThrow(() -> new AccountNotFound(id));
    }
}
