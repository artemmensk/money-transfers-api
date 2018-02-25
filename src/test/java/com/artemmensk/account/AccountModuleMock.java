package com.artemmensk.account;

import com.google.inject.AbstractModule;

import static org.mockito.Mockito.mock;

public class AccountModuleMock extends AbstractModule{
    @Override
    protected void configure() {
        bind(IAccountService.class).to(AccountService.class);
        bind(IAccountRepository.class).toInstance(mock(IAccountRepository.class));
    }
}
