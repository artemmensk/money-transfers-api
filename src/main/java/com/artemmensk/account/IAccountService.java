package com.artemmensk.account;

import java.util.Optional;

public interface IAccountService {
    Account create();
    Optional<Account> findById(Long id);
}
