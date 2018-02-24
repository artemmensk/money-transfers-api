package com.artemmensk.transfer;

public interface ITransferRepository {
    Transfer create(Long from, Long to, Integer amount);
}
