package com.example.integration.separate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
public class SeparateIT {

    @BeforeAll
    public static void setUpAll() {
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Test
    @Sql("classpath:sql/setup_test_data.sql")
    void test_従業員情報が取得できない場合() {
        given()
            .when()
                .pathParam("id", "9999")
                .get("/api/v1/employees/{id}")
            .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("code", equalTo("0001"))
                .body("message", equalTo("specified employee (id=9999) is not found."));
    }

}