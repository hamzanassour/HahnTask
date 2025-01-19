package com.hahn.emsystem.web;

import com.hahn.emsystem.dto.request.EmployeeRequest;
import com.hahn.emsystem.dto.request.ManagerUpdateEmployeeRequest;
import com.hahn.emsystem.dto.response.EmployeeResponse;
import com.hahn.emsystem.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Employee API", description = "Endpoints for managing employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Operation(summary = "Create a new employee", description = "Allows HR or Admin to create a new employee.")
    @PreAuthorize("hasRole('ROLE_HR') or hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(@Valid @RequestBody EmployeeRequest request) {
        EmployeeResponse response = employeeService.createEmployee(request);
        return ResponseEntity.status(201).body(response);
    }

    @Operation(summary = "Get all employees", description = "Allows managers, HR, or Admin to view all employees.")
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_HR') or hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<?>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @Operation(summary = "Delete an employee", description = "Allows HR or Admin to delete an employee by ID.")
    @PreAuthorize("hasRole('ROLE_HR') or hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update an employee", description = "Allows HR or Admin to update an employee's details.")
    @PreAuthorize("hasRole('ROLE_HR') or hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequest request) {
        EmployeeResponse response = employeeService.updateEmployee(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Partially update an employee", description = "Allows managers, HR, or Admin to partially update an employee's details.")
    @PreAuthorize("hasRole('ROLE_HR') or hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    @PatchMapping("/{id}")
    public ResponseEntity<EmployeeResponse> limitedEmployeeUpdate(
            @PathVariable Long id,
            @Valid @RequestBody ManagerUpdateEmployeeRequest request) {
        EmployeeResponse response = employeeService.limitedEmployeeUpdate(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get employee by ID", description = "Retrieve employee details by ID.")
    @PreAuthorize("hasRole('ROLE_HR') or hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable Long id) {
        EmployeeResponse response = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Search employees", description = "Search employees by criteria such as name, ID, status, department, or hire date.")
    @GetMapping("/search")
    public List<?> searchEmployees(
            @Parameter(description = "Search term for name, ID, department, or job title") @RequestParam(required = false) String searchTerm,
            @Parameter(description = "Filter by employment status") @RequestParam(required = false) String status,
            @Parameter(description = "Filter by department") @RequestParam(required = false) String department,
            @Parameter(description = "Filter by hire date in YYYY-MM-DD format") @RequestParam(required = false) String hireDate
    ) {
        return employeeService.searchEmployees(searchTerm, status, department, hireDate);
    }
}
