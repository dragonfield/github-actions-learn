package com.example.demo.presentation;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import com.example.demo.application.service.EmployeeService;
import com.example.demo.common.SystemException;
import com.example.demo.domain.model.Employee;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class EmployeeControllerV1Test {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void test_正常に従業員情報が取得できる場合() {
        // setup
        when(employeeService.find("0001"))
                .thenReturn(Optional.of(Employee.builder()
                                                .id("0001")
                                                .lastName("Taro")
                                                .firstName("Yamada")
                                                .build()));

        // execute & assert
        given()
            .when()
            .get("/api/v1/employees/0001")
            .then()
            .status(HttpStatus.OK)
            .body("id", equalTo("0001"))
            .body("firstName", equalTo("Taro"))
            .body("lastName", equalTo("Yamada"));
    }

    @Test
    void test_従業員情報が取得できない場合() {
        // setup
        when(employeeService.find("9999"))
                .thenReturn(Optional.empty());

        // execute & assert
        given()
            .when()
                .get("/api/v1/employees/9999")
            .then()
                .status(HttpStatus.BAD_REQUEST)
                .body("code", equalTo("0001"))
                .body("message", equalTo("specified employee (id=9999) is not found."));
    }

    @Test
    void test_システム障害が発生した場合() {
        // setup
        when(employeeService.find("9999"))
                .thenThrow(new SystemException());

        // execute & assert
        given()
                .when()
                .get("/api/v1/employees/9999")
                .then()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("code", equalTo("0000"))
                .body("message", equalTo("unexpected exception is occurred."));
    }

}