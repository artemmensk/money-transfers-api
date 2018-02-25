package com.artemmensk.account;

import com.artemmensk.exception.AccountNotFound;

public interface IAccountService {
    Account create();
    Account findById(Long id) throws AccountNotFound;
}
