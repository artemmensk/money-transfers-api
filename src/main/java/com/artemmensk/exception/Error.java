package com.artemmensk.exception;

import lombok.Getter;
import lombok.extern.log4j.Log4j;

@Getter
@Log4j
public class Error {
    private final String message;

    public Error(Exception exception) {
        this.message = exception.getMessage();
        log.error(this.message, exception);
    }
}