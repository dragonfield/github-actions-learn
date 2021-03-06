package com.example.integration.suite;

import static com.example.integration.TestDatum.*;
import static com.ninja_squad.dbsetup.Operations.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import com.example.integration.DemoApplication;
import com.example.integration.IntegrationTestConfig;
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
public class SuiteIT {

    private static IntegrationTestConfig conf = new IntegrationTestConfig();

    @BeforeAll
    public static void setUpAll() {
        RestAssured.baseURI = "http://" + conf.getRestAssuredHost() + ":" + conf.getRestAssuredPort();
    }

    @BeforeEach
    public void setUpDatabase() {
        Destination destination
                = new DriverManagerDestination(conf.getDatabaseUrl(),
                                               conf.getDatabaseUser(),
                                               conf.getDatabasePassword());
        Operation operations = sequenceOf(DELETE_ALL, INSERT_EMPLOYEE);

        DbSetup dbSetup = new DbSetup(destination, operations);
        dbSetup.launch();
    }

    @Test
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