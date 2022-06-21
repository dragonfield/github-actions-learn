package com.example.demo.infrastructure.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.demo.domain.model.Employee;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmployeeRepositoryImplTest {

    @Autowired
    EmployeeRepositoryImpl target;

    @Test
    void test_正常に指定したIDでエンティティが取得できる場合() {
        // setup

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
    void test_指定したIDでエンティティが取得できない場合() {
        // setup

        // execute
        Optional<Employee> actual = target.find("9999");

        // assert
        Optional<Employee> expected = Optional.empty();
        assertThat(actual).isEqualTo(expected);
    }

}