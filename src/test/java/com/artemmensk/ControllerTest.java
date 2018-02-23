package com.artemmensk;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static spark.Spark.*;

public class ControllerTest {

    private final static String BASE_URI = "http://localhost:4567";

    @BeforeMethod
    static void before() {
        Application.main(null);
        awaitInitialization();
    }

    @AfterMethod
    static void after() {
        stop();
    }

    @Test
    void test() {
        given().
                baseUri(BASE_URI).
        when().
                get("/transfer/500/from/A/to/B").
        then().
                statusCode(200);
    }
}
