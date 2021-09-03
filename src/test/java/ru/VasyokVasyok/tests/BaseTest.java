package ru.VasyokVasyok.tests;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static org.hamcrest.Matchers.lessThan;

public class BaseTest {
    static Properties properties;
    static String host;
    static String username;
    static String token;
    static String actoken;
    static ResponseSpecification positiveResponseSpecification;
    static RequestSpecification requestSpecification;

    @BeforeAll
    static void beforeAll() throws IOException {
        positiveResponseSpecification = new ResponseSpecBuilder()
                .expectResponseTime(lessThan(15000L))
                .expectStatusCode(200)
                .expectBody("success", CoreMatchers.is(true))
                .build();

        properties = new Properties();
        properties.load(new FileInputStream("src/test/resources/application.properties"));
        host = properties.getProperty("host");
        username = properties.getProperty("username", "testprogmath");
        token = properties.getProperty("token");
        actoken = properties.getProperty("actoken");

        RestAssured.baseURI = host;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        requestSpecification = new RequestSpecBuilder()
                .addHeader("Authorization", token)
                .log(LogDetail.ALL)
                .build();

        RestAssured.responseSpecification = positiveResponseSpecification;
        RestAssured.requestSpecification = requestSpecification;
    }
}
