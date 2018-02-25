package com.artemmensk.exception;

import lombok.Getter;

@Getter
public class Error {
    private final String message;

    public Error(Exception exception) {
        this.message = exception.getMessage();
    }
}