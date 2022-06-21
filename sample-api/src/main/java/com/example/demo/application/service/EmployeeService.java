package com.example.demo.application.service;

import com.example.demo.application.repository.EmployeeRepository;
import com.example.demo.domain.model.Employee;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository repository;

    public Optional<Employee> find(String id) {
        return repository.find(id);
    }

}
