package com.example.demo.domain.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Employee {

    String id;

    String lastName;

    String firstName;

}
