package com.example.demo.infrastructure.mapper;

import static org.assertj.core.api.Assertions.*;

import com.example.demo.infrastructure.entity.EmployeeEntity;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

@MybatisTest
class EmployeeMapperTest {

    @Autowired
    EmployeeMapper target;

    @Test
    void test_正常に指定したIDでエンティティが取得できる場合() {
        // setup

        // execute
        EmployeeEntity actual = target.find("0001");

        // assert
        EmployeeEntity expected = EmployeeEntity.builder()
                                                .id("0001")
                                                .firstName("Taro")
                                                .lastName("Yamada")
                                                .build();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void test_指定したIDでエンティティが取得できない場合() {
        // setup

        // execute
        EmployeeEntity actual = target.find("9999");

        // assert
        assertThat(actual).isNull();
    }

}