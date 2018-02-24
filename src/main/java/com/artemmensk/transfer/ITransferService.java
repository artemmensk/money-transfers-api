package com.artemmensk.transfer;

import com.artemmensk.exception.AccountNotFound;
import com.artemmensk.exception.NotEnoughBalance;

public interface ITransferService {
    Transfer transfer(Integer amount, Long from, Long to) throws AccountNotFound, NotEnoughBalance;
}
