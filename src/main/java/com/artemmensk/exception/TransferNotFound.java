package com.artemmensk.exception;

public class TransferNotFound extends ATransferException {
    public TransferNotFound(String uuid) {
        super(String.format(ErrorMessage.TRANSFER_NOT_FOUND_WITH_UUID.getMessage(), uuid));
    }

    public TransferNotFound(Long id) {
        super(String.format(ErrorMessage.NOT_FOUND_TRANSFERS_FOR_ACCOUNT.getMessage(), id));
    }

    public TransferNotFound() {
        super(ErrorMessage.TRANSFER_NOT_FOUND.getMessage());
    }
}
