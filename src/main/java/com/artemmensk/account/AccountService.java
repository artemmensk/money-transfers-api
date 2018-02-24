package com.artemmensk.account;

import com.google.inject.Inject;

import java.util.Optional;

public class AccountService implements IAccountService {

    private IAccountRepository accountRepository;

    @Inject
    public AccountService(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account create() {
        return null;
    }

    @Override
    public Optional<Account> findById(Long id) {
        return Optional.empty();
    }
}
