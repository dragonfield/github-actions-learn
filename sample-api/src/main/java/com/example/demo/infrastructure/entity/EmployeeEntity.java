package com.example.demo.infrastructure.entity;

import com.example.demo.domain.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEntity {

    private String id;

    private String lastName;

    private String firstName;

    public Employee toModel() {
        return Employee.builder()
                       .id(id)
                       .firstName(firstName)
                       .lastName(lastName)
                       .build();
    }

}
