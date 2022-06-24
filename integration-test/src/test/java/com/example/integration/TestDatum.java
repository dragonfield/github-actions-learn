package com.example.integration;

import static com.ninja_squad.dbsetup.Operations.*;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.Destination;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import com.ninja_squad.dbsetup.operation.Operation;

public class TestDatum {

    public static final Operation DELETE_ALL = deleteAllFrom("employee");

    public static final Operation INSERT_EMPLOYEE
            = insertInto("employee")
                        .columns("id", "first_name", "last_name")
                        .values("0001", "Taro", "Yamada")
                        .values("0002", "Jiro", "Yamada")
                        .values("0003", "Hanako", "Yamada")
                        .build();
}
