package com.artemmensk.transfer;

import com.artemmensk.account.Account;
import com.artemmensk.account.AccountRepository;
import com.artemmensk.account.IAccountRepository;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TransferModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ITransferService.class).to(TransferService.class).in(Singleton.class);
        bind(ITransferRepository.class).to(TransferRepository.class).in(Singleton.class);
        bind(new TypeLiteral<Map<String, Transfer>>(){}).annotatedWith(Names.named("transfers")).toInstance(new HashMap<>());
    }
}
