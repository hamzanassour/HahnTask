package com.hahn.emsystem.service.impl;

import com.hahn.emsystem.dto.request.EmployeeRequest;
import com.hahn.emsystem.dto.request.ManagerUpdateEmployeeRequest;
import com.hahn.emsystem.dto.response.EmployeeResponse;
import com.hahn.emsystem.entity.Employee;
import com.hahn.emsystem.mapper.EmployeeMapper;
import com.hahn.emsystem.repository.EmployeeRepository;
import com.hahn.emsystem.service.EmployeeService;
import com.hahn.emsystem.utils.UserUtils;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;


    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public EmployeeResponse createEmployee(EmployeeRequest request) {
        Employee employee = employeeMapper.toEntity(request);
        Employee savedEmployee = employeeRepository.save(employee);
        return employeeMapper.toResponse(savedEmployee);
    }

    @Override
    public List<?> getAllEmployees() {
        if (UserUtils.hasRole("ROLE_MANAGER")) {
            Employee manager = employeeRepository.findByUsername(UserUtils.getAuthenticatedUserUsername())
                    .orElseThrow(() -> new IllegalArgumentException("Manager not found"));

            List<Employee> employees = employeeRepository.findAllByDepartment(manager.getDepartment());
            return employeeMapper.toManagerResponseList(employees);
        }
        List<Employee> employees = employeeRepository.findAll();
        return employeeMapper.toResponseList(employees);
    }


    @Override
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new IllegalArgumentException("Employee with id " + id + " does not exist");
        }
        employeeRepository.deleteById(id);
    }

    @Override
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {
        if (!employeeRepository.existsById(id)){
            throw   new IllegalArgumentException("Employee with id " + id + " does not exist");
        }
        Employee updatedEmployee = employeeMapper.toEntity(request);
        updatedEmployee.setId(id);
        Employee savedEmployee = employeeRepository.save(updatedEmployee);
        return employeeMapper.toResponse(savedEmployee);
    }


    @Override
    public EmployeeResponse limitedEmployeeUpdate(Long id, ManagerUpdateEmployeeRequest request) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee with id " + id + " does not exist"));

        if (UserUtils.hasRole("ROLE_MANAGER")) {
            Employee manager = employeeRepository.findByUsername(UserUtils.getAuthenticatedUserUsername())
                    .orElseThrow(() -> new IllegalArgumentException("Manager not found"));
            if (!manager.getDepartment().equals(employee.getDepartment())) {
                throw new IllegalArgumentException("You are not allowed to update employees from other departments");
            }
        }

        employeeMapper.updateEntityFromRequest(request, employee);
        Employee savedEmployee = employeeRepository.save(employee);
        return employeeMapper.toResponse(savedEmployee);
    }

    @Override
    public EmployeeResponse getEmployeeByUsername(String username) {
        Employee employee = employeeRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Employee with username " + username + " does not exist"));
        return employeeMapper.toResponse(employee);
    }

    @Override
    public EmployeeResponse getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee with id " + id + " does not exist"));
        return employeeMapper.toResponse(employee);
    }



    public List<?> searchEmployees(String searchTerm, String status, String department, String hireDate) {
        Specification<Employee> spec = Specification.where(null);  // Start with a null specification
        if (searchTerm != null && !searchTerm.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> {
                Predicate namePredicate = criteriaBuilder.like(root.get("fullName"), "%" + searchTerm.trim()+ "%");
                Predicate idPredicate = criteriaBuilder.like(root.get("id").as(String.class), "%" + searchTerm.trim() + "%");
                Predicate departmentPredicate = criteriaBuilder.like(root.get("department"), "%" + searchTerm.trim()+ "%");
                Predicate jobTitlePredicate = criteriaBuilder.like(root.get("jobTitle"), "%" +  searchTerm.trim()+ "%");
                return criteriaBuilder.or(namePredicate, idPredicate, departmentPredicate, jobTitlePredicate);
            });
        }
        if (status != null && !status.equals("All")) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("employmentStatus"), status));
        }

        if (department != null && !department.equals("All")) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("department"), department));
        }

        if (hireDate != null && !hireDate.isEmpty()) {
            LocalDate parsedHireDate = LocalDate.parse(hireDate);
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("hireDate"), parsedHireDate));
        }

        List<Employee> employees = employeeRepository.findAll(spec);

        // Return the response list based on the user's role
        if (UserUtils.hasRole("ROLE_MANAGER")) {
            return employeeMapper.toManagerResponseList(employees);
        }

        return employeeMapper.toResponseList(employees);
    }
}
