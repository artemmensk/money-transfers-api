package com.artemmensk.exception;

public class NotEnoughBalance extends ATransferException {
    public NotEnoughBalance(Long id) {
        super(String.valueOf(id));
    }
}
