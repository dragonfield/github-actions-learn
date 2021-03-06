package com.example.demo.presentation;

import com.example.demo.domain.model.Employee;
import com.fasterxml.jackson.annotation.JsonProperty;

public record EmployeeResponse(
        @JsonProperty("id")
        String id,
        @JsonProperty("firstName")
        String firstName,
        @JsonProperty("lastName")
        String lastName) {

    public static EmployeeResponse of(Employee employee) {
        return new EmployeeResponse(employee.getId(), employee.getFirstName(), employee.getLastName());
    }

}
