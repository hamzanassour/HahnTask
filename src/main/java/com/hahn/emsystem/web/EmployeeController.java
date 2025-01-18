package com.hahn.emsystem.web;
import com.hahn.emsystem.dto.request.EmployeeRequest;
import com.hahn.emsystem.dto.request.ManagerUpdateEmployeeRequest;
import com.hahn.emsystem.dto.response.EmployeeResponse;
import com.hahn.emsystem.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@Validated
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PreAuthorize("hasRole('ROLE_HR') or hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(@Valid @RequestBody EmployeeRequest request) {
        EmployeeResponse response = employeeService.createEmployee(request);
        return ResponseEntity.status(201).body(response);
    }


    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_HR') or hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<?>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }


    @PreAuthorize("hasRole('ROLE_HR') or hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_HR') or hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequest request) {
        EmployeeResponse response = employeeService.updateEmployee(id, request);
        return ResponseEntity.ok(response);
    }


    @PreAuthorize("hasRole('ROLE_HR') or hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    @PatchMapping("/{id}")
    public ResponseEntity<EmployeeResponse> limitedEmployeeUpdate(
            @PathVariable Long id,
            @Valid @RequestBody ManagerUpdateEmployeeRequest request) {
        EmployeeResponse response = employeeService.limitedEmployeeUpdate(id, request);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_HR') or hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable Long id) {
        EmployeeResponse response = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/search")
    public List<?> searchEmployees(
            @RequestParam(required = false) String searchTerm, // Search term for name, ID, department, or job title
            @RequestParam(required = false) String status,    // Filter by employment status
            @RequestParam(required = false) String department, // Filter by department
            @RequestParam(required = false) String hireDate    // Filter by hire date
    ) {
        // Call the service to fetch filtered and searched employees
        return employeeService.searchEmployees(searchTerm, status, department, hireDate);
    }
}
