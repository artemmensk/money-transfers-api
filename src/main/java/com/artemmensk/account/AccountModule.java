package com.artemmensk.account;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class AccountModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IAccountRepository.class).to(AccountRepository.class).in(Singleton.class);
        bind(IAccountService.class).to(AccountService.class).in(Singleton.class);
    }
}
