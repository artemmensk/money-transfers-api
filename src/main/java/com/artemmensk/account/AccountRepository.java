package com.artemmensk.account;


import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.util.Map;
import java.util.Optional;

public class AccountRepository implements IAccountRepository {

    private final Map<Long, Account> accounts;

    @Inject
    public AccountRepository(@Named("accounts") Map<Long, Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public Account create() {
        final Account account = new Account();
        accounts.put(account.getId(), account);
        return account;
    }

    @Override
    public Optional<Account> findById(Long id) {
        return Optional.ofNullable(accounts.get(id));
    }
}
