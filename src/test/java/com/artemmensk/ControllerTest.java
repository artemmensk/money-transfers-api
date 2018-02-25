package com.artemmensk;

import com.artemmensk.account.Account;
import com.artemmensk.account.IAccountService;
import com.artemmensk.exception.AccountNotFound;
import com.artemmensk.exception.ErrorMessage;
import com.artemmensk.exception.NotEnoughBalance;
import com.artemmensk.transfer.ITransferService;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpStatus;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.equalTo;
import static spark.Spark.*;

public class ControllerTest {

    private static final String ACCOUNT_URI = "http://localhost:4567/account";
    private static final Account ACCOUNT = new Account();

    private final ITransferService transferService;
    private final IAccountService accountService;

    private ControllerTest() {
        final Injector injector = Guice.createInjector(new ControllerModuleMock());
        this.transferService = injector.getInstance(ITransferService.class);
        this.accountService = injector.getInstance(IAccountService.class);
        injector.getInstance(IController.class).setUpEndpoints();
        awaitInitialization();
    }

    @BeforeMethod
    private void beforeEachTest() {
        org.mockito.Mockito.reset(transferService, accountService);
    }

    @Test
    void createAccount() {
        org.mockito.Mockito.when(accountService.create()).thenReturn(ACCOUNT);

        when()
                .post(ACCOUNT_URI).
        then()
                .statusCode(HttpStatus.CREATED_201)
                .header(HttpHeader.LOCATION.name(), ACCOUNT_URI + "/" + ACCOUNT.getId());
    }

    @Test
    public void findAccountById() throws AccountNotFound {
        org.mockito.Mockito.when(accountService.findById(ACCOUNT.getId())).thenReturn(ACCOUNT);

        when()
                .get(ACCOUNT_URI + "/" + ACCOUNT.getId()).
        then()
                .statusCode(HttpStatus.OK_200)
                .body("id", equalTo(ACCOUNT.getId().intValue()))
                .body("balance", equalTo(ACCOUNT.getBalance()));
    }

    @Test
    public void findNonExistingAccountById() throws AccountNotFound {
        org.mockito.Mockito.when(accountService.findById(ACCOUNT.getId())).thenThrow(new AccountNotFound(ACCOUNT.getId()));

        when()
                .get(ACCOUNT_URI + "/" + ACCOUNT.getId()).
        then()
                .statusCode(HttpStatus.NOT_FOUND_404)
                .body("message", equalTo(String.format(ErrorMessage.ACCOUNT_NOT_FOUND.getMessage(), ACCOUNT.getId())));
    }

    @Test
    public void badRequest() {
        when()
                .get(ACCOUNT_URI + "/a").
        then()
                .statusCode(HttpStatus.BAD_REQUEST_400);
    }
}
