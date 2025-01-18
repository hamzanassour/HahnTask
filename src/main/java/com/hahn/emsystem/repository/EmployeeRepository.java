package com.hahn.emsystem.repository;


import com.hahn.emsystem.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
    boolean existsById(Long id);

    Optional<Employee> findByUsername(String username);

    List<Employee> findAllByDepartment(String department);
}
