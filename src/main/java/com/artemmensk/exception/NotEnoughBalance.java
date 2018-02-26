package com.artemmensk.exception;

public class NotEnoughBalance extends Exception {
    public NotEnoughBalance(Integer balance) {
        super(String.format(ErrorMessage.NOT_ENOUGH_BALANCE.getMessage(), balance));
    }
}
