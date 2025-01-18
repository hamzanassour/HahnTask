package com.hahn.emsystem.service;


import com.hahn.emsystem.dto.request.EmployeeRequest;
import com.hahn.emsystem.dto.request.ManagerUpdateEmployeeRequest;
import com.hahn.emsystem.dto.response.EmployeeResponse;
import com.hahn.emsystem.entity.Employee;

import java.util.List;

public interface EmployeeService {
    EmployeeResponse createEmployee(EmployeeRequest employee);
    List<?> getAllEmployees();
    void deleteEmployee(Long id);
    EmployeeResponse updateEmployee(Long id, EmployeeRequest request);
    EmployeeResponse limitedEmployeeUpdate(Long id, ManagerUpdateEmployeeRequest request);

    EmployeeResponse getEmployeeByUsername(String username);

    EmployeeResponse getEmployeeById(Long id);
    List<?> searchEmployees(String searchTerm, String status, String department, String hireDate);
}