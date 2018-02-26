package com.artemmensk.exception;

public class TheSameAccount extends Exception {
    public TheSameAccount() {
        super(ErrorMessage.THE_SAME_ACCOUNT.getMessage());
    }
}
