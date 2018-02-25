package com.artemmensk.exception;

public class TheSameAccount extends ATransferException {
    public TheSameAccount() {
        super(ErrorMessage.THE_SAME_ACCOUNT.getMessage());
    }
}
