package com.artemmensk.account;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AccountModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IAccountRepository.class).to(AccountRepository.class).in(Singleton.class);
        bind(IAccountService.class).to(AccountService.class).in(Singleton.class);
        bind(new TypeLiteral<Map<Long, Account>>(){}).annotatedWith(Names.named("accounts")).toInstance(new ConcurrentHashMap<>());
    }
}
