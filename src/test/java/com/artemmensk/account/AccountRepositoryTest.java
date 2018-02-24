package com.artemmensk.account;

import com.google.inject.Inject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

@Guice(modules = AccountModule.class)
public class AccountRepositoryTest {

    private IAccountRepository repository;

    @Inject
    public AccountRepositoryTest(IAccountRepository repository) {
        this.repository = repository;
    }

    @BeforeMethod
    public void setUp() {

    }

    @Test
    public void createAccount() {
        // when
        final Account account = repository.create();

        // then
        Assert.assertEquals(account.getId(), new Long(1L));
        Assert.assertEquals(account.getBalance(), new Integer(0));
    }

    @Test
    public void findAccount() {
        // given
        repository.create();

        // when

        // then
        System.out.println(repository.findById(2L));
    }
}
