package ru.VasyokVasyok.tests;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class AccountTests extends BaseTest {
ResponseSpecification accountResponseSpec;

    @Test
    void getAccountPositiveTest() {
        accountResponseSpec = positiveResponseSpecification
                .expect()
                .body("data.url", equalTo(username));

        given(requestSpecification, accountResponseSpec)
                .get("account/{username}", username)
                .prettyPeek();
    }
}
