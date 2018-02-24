package com.artemmensk.account;


import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class AccountRepository implements IAccountRepository {

    private Map<Long, Account> accounts = new ConcurrentHashMap<>();
    private static Long id = 1L;

    @Override
    public Account create() {
        Account account = new Account(id++);
        accounts.put(account.getId(), account);
        return account;
    }

    @Override
    public Optional<Account> findById(Long id) {
        return Optional.ofNullable(accounts.get(id));
    }
}
