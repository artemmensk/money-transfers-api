package com.artemmensk;

import com.artemmensk.account.AccountModule;
import com.artemmensk.account.IAccountService;
import com.artemmensk.exception.AccountNotFound;
import com.artemmensk.exception.NotEnoughBalance;
import com.artemmensk.transfer.ITransferService;
import com.artemmensk.transfer.Transfer;
import com.artemmensk.transfer.TransferModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.util.Modules;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static spark.Spark.*;

@Guice(modules = {ApplicationModuleTest.class})
public class ControllerTest {

    private final static String BASE_URI = "http://localhost:4567";

    private ITransferService transferService;
    private IAccountService accountService;

    @Inject
    public ControllerTest(ITransferService transferService, IAccountService accountService) {
        this.transferService = transferService;
        this.accountService = accountService;
    }

    @BeforeMethod
    static void before() {
        Modules.override(new ApplicationModule()).with(new ApplicationModuleTest());
        Application.main(null);
        awaitInitialization();
    }

    @AfterMethod
    static void after() {
//        stop();
    }

    @Test
    void test() throws AccountNotFound, NotEnoughBalance {
        when(transferService.transfer(any(), any(), any())).thenReturn(new Transfer(123L, 321L, 500));
        given().
                baseUri(BASE_URI).
        when().
                get("/transfer/500/from/123/to/321").
        then().
                statusCode(200);
    }
}
