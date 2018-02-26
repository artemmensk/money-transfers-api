package com.artemmensk.account;

import java.util.Optional;

/**
 * Repository of account entities.
 */
public interface IAccountRepository {
    /**
     * Creates and returns account.
     *
     * @return created account
     */
    Account create();

    /**
     * Finds and returns Optional with account or Optional with empty
     *
     * @param id identifier of account to find
     * @return Optional with account or with empty
     */
    Optional<Account> findById(Long id);
}
