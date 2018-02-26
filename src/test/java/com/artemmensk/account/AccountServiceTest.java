package com.artemmensk.account;

import com.artemmensk.exception.AccountNotFound;
import com.artemmensk.exception.NegativeDeposit;
import com.google.inject.Inject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Guice(modules = AccountModuleMock.class)
public class AccountServiceTest {

    private static final Account ACCOUNT = new Account();
    private static final Integer AMOUNT = 50;

    private final IAccountService service;
    private final IAccountRepository repository;

    @Inject
    public AccountServiceTest(IAccountService service, IAccountRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @BeforeMethod
    private void beforeEachTest() {
        org.mockito.Mockito.reset(repository);
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
    public void deposit() throws AccountNotFound, NegativeDeposit {
        // given
        when(repository.findById(ACCOUNT.getId())).thenReturn(Optional.ofNullable(ACCOUNT));

        // when
        service.deposit(AMOUNT, ACCOUNT.getId());

        // then
        Assert.assertEquals(ACCOUNT.getBalance(), AMOUNT);
    }

    @Test(expectedExceptions = AccountNotFound.class)
    public void depositForNonExisting() throws AccountNotFound, NegativeDeposit {
        // given
        when(repository.findById(ACCOUNT.getId())).thenReturn(Optional.ofNullable(null));

        // when
        service.deposit(AMOUNT, ACCOUNT.getId());

        // then
        // throws exception

    }

    @Test(expectedExceptions = NegativeDeposit.class)
    public void negativeDeposit() throws AccountNotFound, NegativeDeposit {
        // when
        service.deposit(-AMOUNT, ACCOUNT.getId());

        // then
        // throws exception

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
