package com.example.integration.suite;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import com.example.integration.DemoApplication;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(classes = DemoApplication.class)
public class SuiteIT {

    @BeforeAll
    public static void setUpAll() {
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Test
    @Sql("classpath:sql/setup_test_data.sql")
    void test_正常に従業員情報が取得できる場合() {
        given()
            .when()
                .pathParam("id", "0001")
                .get("/api/v1/employees/{id}")
            .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo("0001"))
                .body("firstName", equalTo("Taro"))
                .body("lastName", equalTo("Yamada"));
    }

}