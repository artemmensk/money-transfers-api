package com.artemmensk.account;

import com.artemmensk.exception.AccountNotFound;
import com.artemmensk.exception.NegativeDeposit;

/**
 * Service for manage accounts.
 */
public interface IAccountService {

    /**
     * Creates and returns account.
     *
     * @return created account
     */
    Account create();

    /**
     * Deposit money in account
     *
     * @param amount of money to deposit
     * @param id identifier of account to deposit
     * @throws AccountNotFound not found account with specific id
     * @throws NegativeDeposit if specific amount is negative
     */
    void deposit(Integer amount, Long id) throws AccountNotFound, NegativeDeposit;

    /**
     * Finds and returns account entity, otherwise throws exception.
     *
     * @param id identifier of account to find
     * @return account with specific id
     * @throws AccountNotFound if not found account with specific id
     */
    Account findById(Long id) throws AccountNotFound;
}
