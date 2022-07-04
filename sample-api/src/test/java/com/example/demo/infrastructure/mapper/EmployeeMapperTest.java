package com.example.demo.infrastructure.mapper;

import static org.assertj.core.api.Assertions.*;

import com.example.demo.infrastructure.entity.EmployeeEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.OracleContainer;
import org.testcontainers.delegate.DatabaseDelegate;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@MybatisTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmployeeMapperTest {

    @Container
    private static final OracleContainer oracleContainer =
            new OracleContainer(DockerImageName.parse("gvenzl/oracle-xe").withTag("11.2.0.2"))
                    .withDatabaseName("XE")
                    .withUsername("appuser")
                    .withPassword("password123");
//                    .withInitScript("schema.sql");

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        // Datasource Config
        registry.add("spring.datasource.driver-class-name", oracleContainer::getDriverClassName);
        registry.add("spring.datasource.url", oracleContainer::getJdbcUrl);
        registry.add("spring.datasource.username", oracleContainer::getUsername);
        registry.add("spring.datasource.password", oracleContainer::getPassword);
    }

    @BeforeAll
    static void setupAll() {
        System.out.println("=========================================================================");
        System.out.println("user.language = " + System.getProperty("user.language"));
        System.out.println("user.country = " + System.getProperty("user.country"));
        System.out.println("user.timezone = " + System.getProperty("user.timezone"));
        System.out.println("oracle.jdbc.timezoneAsRegion = " + System.getProperty("oracle.jdbc.timezoneAsRegion"));
        System.out.println("=========================================================================");
        DatabaseDelegate containerDelegate = new JdbcDatabaseDelegate(oracleContainer, "");
        ScriptUtils.runInitScript(containerDelegate, "schema.sql");

    }

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