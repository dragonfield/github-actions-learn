package com.example.demo.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.demo.application.repository.EmployeeRepository;
import com.example.demo.domain.model.Employee;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class EmployeeServiceTest {

    @InjectMocks
    EmployeeService target;

    @Mock
    EmployeeRepository repository;

    @BeforeEach
    private void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test_正常に指定したIDで従業員のモデルが取得できる場合() {
        // setup
        when(repository.find("0001")).thenReturn(Optional.of(Employee.builder()
                                                                     .id("0001")
                                                                     .firstName("Taro")
                                                                     .lastName("Yamada")
                                                                     .build()));

        // execute
        Optional<Employee> actual = target.find("0001");

        // assert
        Optional<Employee> expected = Optional.of(Employee.builder()
                                                          .id("0001")
                                                          .firstName("Taro")
                                                          .lastName("Yamada")
                                                          .build());
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void test_指定したIDで従業員のモデルが取得できない場合() {
        // setup
        when(repository.find("9999")).thenReturn(Optional.empty());

        // execute
        Optional<Employee> actual = target.find("9999");

        // assert
        Optional<Employee> expected = Optional.empty();
        assertThat(actual).isEqualTo(expected);
    }

}