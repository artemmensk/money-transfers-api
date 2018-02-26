package com.artemmensk.account;

import com.artemmensk.exception.AccountNotFound;
import com.artemmensk.exception.NegativeDeposit;

public interface IAccountService {
    Account create();
    void deposit(Integer amount, Long id) throws AccountNotFound, NegativeDeposit;
    Account findById(Long id) throws AccountNotFound;
}
