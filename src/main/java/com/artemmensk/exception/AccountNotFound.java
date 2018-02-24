package com.artemmensk.exception;

public class AccountNotFound extends ATransferException {
    public AccountNotFound(Long id) {
        super(String.valueOf(id));
    }
}
