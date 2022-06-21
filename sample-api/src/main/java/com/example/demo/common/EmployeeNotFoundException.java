package com.example.demo.common;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class EmployeeNotFoundException extends RuntimeException {

    private final String id;

    public EmployeeNotFoundException(String id) {
        super();
        this.id = id;
    }

}
