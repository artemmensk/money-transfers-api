package com.artemmensk.exception;

public class NotEnoughBalance extends ATransferException {
    public NotEnoughBalance(Integer balance) {
        super(String.format(ErrorMessage.NOT_ENOUGH_BALANCE.getMessage(), balance));
    }
}
