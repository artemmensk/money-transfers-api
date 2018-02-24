package com.artemmensk.transfer;

import com.artemmensk.Controller;
import com.artemmensk.IController;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class TransferModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ITransferService.class).to(TransferService.class).in(Singleton.class);
        bind(ITransferRepository.class).to(TransferRepository.class).in(Singleton.class);
    }
}
