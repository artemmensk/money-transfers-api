package com.artemmensk.transfer;

import com.artemmensk.account.AccountRepository;
import com.artemmensk.account.IAccountRepository;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;

public class TransferModuleMock extends AbstractModule {
    @Override
    protected void configure() {
        bind(ITransferService.class).to(TransferService.class).in(Singleton.class);
        bind(ITransferRepository.class).toInstance(mock(ITransferRepository.class));
        bind(IAccountRepository.class).toInstance(mock(IAccountRepository.class));
        bind(new TypeLiteral<Map<String, Transfer>>(){}).annotatedWith(Names.named("transfers")).toInstance(new HashMap<>());
    }
}
