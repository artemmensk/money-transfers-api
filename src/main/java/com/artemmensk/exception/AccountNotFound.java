package com.artemmensk.exception;

public class AccountNotFound extends Exception {
    public AccountNotFound(Long id) {
        super(String.format(ErrorMessage.ACCOUNT_NOT_FOUND.getMessage(), id));
    }
}
