package com.example.integration.separate;

import static com.example.integration.TestDatum.*;
import static com.ninja_squad.dbsetup.Operations.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import com.example.integration.DemoApplication;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.Destination;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

@SpringBootTest(classes = DemoApplication.class)
public class SeparateIT {

    @BeforeAll
    public static void setUpAll() {
        RestAssured.baseURI = "http://localhost:8080";
    }

    @BeforeEach
    public void setUpDatabase() {
        Destination destination
                = new DriverManagerDestination("jdbc:postgresql://localhost:5432/mydb",
                                               "appuser",
                                               "password123");
        Operation operations = sequenceOf(DELETE_ALL, INSERT_EMPLOYEE);

        DbSetup dbSetup = new DbSetup(destination, operations);
        dbSetup.launch();
    }

    @Test
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