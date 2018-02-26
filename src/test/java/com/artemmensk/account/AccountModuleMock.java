package com.artemmensk.account;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.mockito.Mockito.mock;

public class AccountModuleMock extends AbstractModule{
    @Override
    protected void configure() {
        bind(IAccountService.class).to(AccountService.class);
        bind(IAccountRepository.class).toInstance(mock(IAccountRepository.class));
        bind(new TypeLiteral<Map<Long, Account>>(){}).annotatedWith(Names.named("accounts")).toInstance(new ConcurrentHashMap<>());
    }
}
