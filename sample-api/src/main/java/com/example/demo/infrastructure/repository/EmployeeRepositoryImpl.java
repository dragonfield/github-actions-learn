package com.example.demo.infrastructure.repository;

import static java.util.Objects.nonNull;

import com.example.demo.application.repository.EmployeeRepository;
import com.example.demo.common.SystemException;
import com.example.demo.domain.model.Employee;
import com.example.demo.infrastructure.entity.EmployeeEntity;
import com.example.demo.infrastructure.mapper.EmployeeMapper;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

    @Autowired
    EmployeeMapper mapper;

    @Override
    public Optional<Employee> find(String id) {
        Employee result = null;

        try {
            EmployeeEntity entity = mapper.find(id);

            if (nonNull(entity)) {
                result = entity.toModel();
            }
        } catch (DataAccessException e) {
            throw new SystemException(e);
        }

        return Optional.ofNullable(result);
    }

}
