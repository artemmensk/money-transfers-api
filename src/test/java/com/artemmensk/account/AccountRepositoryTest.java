package com.artemmensk.account;

import com.google.inject.Inject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.Optional;

@Guice(modules = AccountModule.class)
public class AccountRepositoryTest {

    private static final Account ACCOUNT = new Account();

    private final IAccountRepository repository;
    private final Map<Long, Account> accounts;

    @Inject
    public AccountRepositoryTest(IAccountRepository repository) {
        this.repository = repository;
        accounts = ((AccountRepository)repository).getAccounts();
    }

    @BeforeMethod
    public void beforeEachTest() {
        accounts.clear();
    }

    @Test
    public void createAccount() {
        // when
        final Account account = repository.create();

        // then
        Assert.assertEquals(accounts.size(), 1);
        Assert.assertEquals(account, accounts.get(account.getId()));
        Assert.assertEquals(account.getBalance(), new Integer(0));
    }

    @Test
    public void findAccountById() {
        // given
        accounts.put(ACCOUNT.getId(), ACCOUNT);

        // when
        final Account account = repository.findById(ACCOUNT.getId()).get();

        // then
        Assert.assertEquals(account, ACCOUNT);
    }

    @Test
    public void findNonExistingAccountById() {
        // when
        final Optional<Account> account = repository.findById(ACCOUNT.getId());

        // then
        Assert.assertFalse(account.isPresent());
    }
}
