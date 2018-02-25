package com.artemmensk.account;


import lombok.Getter;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class AccountRepository implements IAccountRepository {

    private final Map<Long, Account> accounts = new ConcurrentHashMap<>();

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
