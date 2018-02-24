package com.artemmensk.account;

import java.util.Optional;

public interface IAccountRepository {
    Account create();
    Optional<Account> findById(Long id);
}
