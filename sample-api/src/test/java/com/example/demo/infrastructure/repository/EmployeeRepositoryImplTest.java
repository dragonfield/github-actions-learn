package com.example.demo.infrastructure.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.demo.domain.model.Employee;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.OracleContainer;
import org.testcontainers.delegate.DatabaseDelegate;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@Testcontainers
class EmployeeRepositoryImplTest {

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
        registry.add("logging.level.org.testcontainers", () -> "INFO");
        registry.add("logging.level.com.github.dockerjava", () -> "WARN");
    }

    @BeforeAll
    static void setupAll() {
        String systemProperty = System.getProperty("oracle.jdbc.timezoneAsRegion");
        System.out.println("============" + systemProperty + "============");
        DatabaseDelegate containerDelegate = new JdbcDatabaseDelegate(oracleContainer, "");
        ScriptUtils.runInitScript(containerDelegate, "schema.sql");
    }

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