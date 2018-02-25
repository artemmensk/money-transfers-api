package com.artemmensk.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorMessage {
    ACCOUNT_NOT_FOUND("Account with id: %s not found");

    private final String message;
}
