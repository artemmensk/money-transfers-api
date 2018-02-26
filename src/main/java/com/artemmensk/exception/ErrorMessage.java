package com.artemmensk.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorMessage {
    ACCOUNT_NOT_FOUND("Not found account with id: %s"),
    TRANSFER_NOT_FOUND("Not found any transfers"),
    TRANSFER_NOT_FOUND_WITH_UUID("Not found transfer with uuid: %s"),
    NOT_FOUND_TRANSFERS_FOR_ACCOUNT("Not found transfers for account with id: %s"),
    NOT_ENOUGH_BALANCE("Not enough balance: %s"),
    THE_SAME_ACCOUNT("The same account"),
    NEGATIVE_DEPOSIT("Deposit can not be negative");

    private final String message;
}
