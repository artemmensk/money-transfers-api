package com.artemmensk.account;

import com.artemmensk.exception.AccountNotFound;
import com.google.inject.Inject;
import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.mockito.Mockito.when;

@Guice(modules = AccountModuleMock.class)
public class AccountServiceTest {

    private static final Account ACCOUNT = new Account();

    private final IAccountService service;
    private final IAccountRepository repository;

    @Inject
    public AccountServiceTest(IAccountService service, IAccountRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @Test
    public void createAccount() {
        // given
        when(repository.create()).thenReturn(ACCOUNT);

        // when
        final Account account = service.create();

        // then
        Assert.assertEquals(account, ACCOUNT);
    }

    @Test
    public void findAccountById() throws AccountNotFound {
        // given
        when(repository.findById(ACCOUNT.getId())).thenReturn(Optional.ofNullable(ACCOUNT));

        // when
        final Account account = service.findById(ACCOUNT.getId());

        // then
        Assert.assertEquals(account, ACCOUNT);
    }

    @Test(expectedExceptions = AccountNotFound.class)
    public void findNonExistingAccountById() throws AccountNotFound {
        // given
        when(repository.findById(ACCOUNT.getId())).thenReturn(Optional.ofNullable(null));

        // when
        service.findById(ACCOUNT.getId());

        // then
        // throws exception
    }
}
