package com.artemmensk.exception;

public class NegativeDeposit extends Exception {
    public NegativeDeposit() {
        super(ErrorMessage.NEGATIVE_DEPOSIT.getMessage());
    }
}
