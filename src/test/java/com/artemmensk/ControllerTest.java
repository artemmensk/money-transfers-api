package com.artemmensk;

import com.artemmensk.account.Account;
import com.artemmensk.account.IAccountService;
import com.artemmensk.exception.AccountNotFound;
import com.artemmensk.exception.ErrorMessage;
import com.artemmensk.exception.TransferNotFound;
import com.artemmensk.transfer.ITransferService;
import com.artemmensk.transfer.Transfer;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.restassured.response.Response;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.equalTo;
import static spark.Spark.*;

public class ControllerTest {

    private static final String ACCOUNT_URI = "http://localhost:4567/account";
    private static final String TRANSFER_URI = "http://localhost:4567/transfer";
    private static final Integer AMOUNT_1 = 10;
    private static final Integer AMOUNT_2 = 300;
    private static final Long ACCOUNT_1_ID = 1L;
    private static final Long ACCOUNT_2_ID = 2L;
    private static final Transfer TRANSFER_1 = new Transfer(AMOUNT_1, ACCOUNT_1_ID, ACCOUNT_2_ID);
    private static final Transfer TRANSFER_2 = new Transfer(AMOUNT_2, ACCOUNT_2_ID, ACCOUNT_1_ID);
    private static final List<Transfer> TRANSFERS = Arrays.asList(TRANSFER_1, TRANSFER_2);
    private static final Account ACCOUNT = new Account();

    private final IAccountService accountService;
    private final ITransferService transferService;

    private ControllerTest() {
        final Injector injector = Guice.createInjector(new ControllerModuleMock());
        this.accountService = injector.getInstance(IAccountService.class);
        this.transferService = injector.getInstance(ITransferService.class);
        injector.getInstance(IController.class).setUpEndpoints();
        awaitInitialization();
    }

    @BeforeMethod
    private void beforeEachTest() {
        org.mockito.Mockito.reset(accountService, transferService);
    }

    @Test
    void createAccount() {
        // given
                org.mockito.Mockito.when(accountService.create()).thenReturn(ACCOUNT);
        when()
                .post(ACCOUNT_URI).
        then()
                .statusCode(HttpStatus.CREATED_201)
                .header(HttpHeader.LOCATION.name(), ACCOUNT_URI + "/" + ACCOUNT.getId());
    }

    @Test
    public void findAccountById() throws AccountNotFound {
        // given
                org.mockito.Mockito.when(accountService.findById(ACCOUNT.getId())).thenReturn(ACCOUNT);
        when()
                .get(ACCOUNT_URI + "/" + ACCOUNT.getId()).
        then()
                .statusCode(HttpStatus.OK_200)
                .body("id", equalTo(ACCOUNT.getId().intValue()))
                .body("balance", equalTo(ACCOUNT.getBalance()));
    }

    @Test
    public void nonExistingAccountById() throws AccountNotFound {
        // given
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

    @Test
    public void getTransfer() throws TransferNotFound {
        // given
                org.mockito.Mockito.when(transferService.getTransfer(TRANSFER_1.getUuid())).thenReturn(TRANSFER_1);

        when()
                .get(TRANSFER_URI + "/" + TRANSFER_1.getUuid()).
        then()
                .statusCode(HttpStatus.OK_200)
                .body("amount", equalTo(TRANSFER_1.getAmount()))
                .body("source", equalTo(TRANSFER_1.getSource().intValue()))
                .body("destination", equalTo(TRANSFER_1.getDestination().intValue()));
    }

    @Test
    public void nonExistingTransfer() throws TransferNotFound {
        // given
                org.mockito.Mockito.when(transferService.getTransfer(TRANSFER_1.getUuid())).thenThrow(new TransferNotFound(TRANSFER_1.getUuid()));

        when()
                .get(TRANSFER_URI + "/" + TRANSFER_1.getUuid()).
        then()
                .statusCode(HttpStatus.NOT_FOUND_404)
                .body("message", equalTo(String.format(ErrorMessage.TRANSFER_NOT_FOUND_WITH_UUID.getMessage(), TRANSFER_1.getUuid())));
    }

    @Test
    public void getTransfersForAccount() throws TransferNotFound {
        // given
                org.mockito.Mockito.when(transferService.getTransfersForAccount(ACCOUNT_1_ID)).thenReturn(TRANSFERS);

        // when
                final Response response = when().get(ACCOUNT_URI + "/" + ACCOUNT_1_ID + "/transfer");
                final List< Transfer> transfers = Arrays.asList(response.as(Transfer[].class));

        // then
                response.then().statusCode(HttpStatus.OK_200);
                Assert.assertEquals(transfers, TRANSFERS);
    }

    @Test
    public void nonExistingTransferForAccount() throws TransferNotFound {
        // given
                org.mockito.Mockito.when(transferService.getTransfersForAccount(TRANSFER_1.getSource())).thenThrow(new TransferNotFound(TRANSFER_1.getSource()));
        when()
                .get(ACCOUNT_URI + "/" + TRANSFER_1.getSource() + "/transfer").
        then()
                .statusCode(HttpStatus.NOT_FOUND_404)
                .body("message", equalTo(String.format(ErrorMessage.NOT_FOUND_TRANSFERS_FOR_ACCOUNT.getMessage(), TRANSFER_1.getSource())));
    }

    @Test
    public void getAllTransfers() throws TransferNotFound {
        // given
                org.mockito.Mockito.when(transferService.getAllTransfers()).thenReturn(TRANSFERS);

        // when
                final Response response = when().get(TRANSFER_URI);
                final List< Transfer> transfers = Arrays.asList(response.as(Transfer[].class));

        // then
                response.then().statusCode(HttpStatus.OK_200);
                Assert.assertEquals(transfers, TRANSFERS);
    }

    @Test
    public void nonExistingTransfersAtAll() throws TransferNotFound {
        // given
                org.mockito.Mockito.when(transferService.getAllTransfers()).thenThrow(new TransferNotFound());
        when()
                .get(TRANSFER_URI).
        then()
                .statusCode(HttpStatus.NOT_FOUND_404)
                .body("message", equalTo(ErrorMessage.TRANSFER_NOT_FOUND.getMessage()));
    }
}
