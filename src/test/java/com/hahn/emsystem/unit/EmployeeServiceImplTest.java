package com.hahn.emsystem.unit;

import com.hahn.emsystem.dto.request.EmployeeRequest;
import com.hahn.emsystem.dto.request.ManagerUpdateEmployeeRequest;
import com.hahn.emsystem.dto.response.EmployeeResponse;
import com.hahn.emsystem.entity.Employee;
import com.hahn.emsystem.mapper.EmployeeMapper;
import com.hahn.emsystem.repository.EmployeeRepository;
import com.hahn.emsystem.service.impl.EmployeeServiceImpl;
import com.hahn.emsystem.utils.UserUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

public class EmployeeServiceImplTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @Mock
    private UserUtils userUtils;


    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateEmployee() {
        EmployeeRequest request = new EmployeeRequest();
        Employee employee = new Employee();
        EmployeeResponse response = new EmployeeResponse();

        when(employeeMapper.toEntity(any(EmployeeRequest.class))).thenReturn(employee);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        when(employeeMapper.toResponse(any(Employee.class))).thenReturn(response);

        EmployeeResponse result = employeeService.createEmployee(request);

        assertThat(result).isNotNull();
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    public void testGetAllEmployees() {
        try (var mockedStatic = mockStatic(UserUtils.class)) {
            mockedStatic.when(() -> UserUtils.hasRole("ROLE_MANAGER")).thenReturn(false);

            when(employeeRepository.findAll()).thenReturn(List.of(new Employee()));
            when(employeeMapper.toResponseList(any(List.class))).thenReturn(List.of(new EmployeeResponse()));

            List<EmployeeResponse> result = (List<EmployeeResponse>) employeeService.getAllEmployees();

            assertThat(result).isNotEmpty();
            verify(employeeRepository, times(1)).findAll();
        }
    }

    @Test
    public void testDeleteEmployee() {
        Long employeeId = 1L;
        when(employeeRepository.existsById(employeeId)).thenReturn(true);

        employeeService.deleteEmployee(employeeId);

        verify(employeeRepository, times(1)).deleteById(employeeId);
    }

    @Test
    public void testUpdateEmployee() {
        Long employeeId = 1L;
        EmployeeRequest request = new EmployeeRequest();
        Employee employee = new Employee();
        EmployeeResponse response = new EmployeeResponse();

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        doNothing().when(employeeMapper).updateEntityFromRequest(any(EmployeeRequest.class), any(Employee.class));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        when(employeeMapper.toResponse(any(Employee.class))).thenReturn(response);
        EmployeeResponse result = employeeService.updateEmployee(employeeId, request);
        assertThat(result).isNotNull();
        verify(employeeRepository, times(1)).save(any(Employee.class));
        verify(employeeMapper, times(1)).updateEntityFromRequest(any(EmployeeRequest.class), any(Employee.class));
        verify(employeeMapper, times(1)).toResponse(any(Employee.class));
    }


    @Test
    public void testLimitedEmployeeUpdate() {
        Long employeeId = 1L;
        ManagerUpdateEmployeeRequest request = new ManagerUpdateEmployeeRequest();
        Employee employee = new Employee();
        EmployeeResponse response = new EmployeeResponse();

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        doNothing().when(employeeMapper).updateEntityFromRequest(any(ManagerUpdateEmployeeRequest.class), any(Employee.class));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        when(employeeMapper.toResponse(any(Employee.class))).thenReturn(response);
        EmployeeResponse result = employeeService.limitedEmployeeUpdate(employeeId, request);
        assertThat(result).isNotNull();
        verify(employeeRepository, times(1)).save(any(Employee.class));
        verify(employeeMapper, times(1)).updateEntityFromRequest(any(ManagerUpdateEmployeeRequest.class), any(Employee.class));
        verify(employeeMapper, times(1)).toResponse(any(Employee.class));
    }


    @Test
    public void testGetEmployeeById() {
        Long employeeId = 1L;
        Employee employee = new Employee();
        EmployeeResponse response = new EmployeeResponse();

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(employeeMapper.toResponse(any(Employee.class))).thenReturn(response);

        EmployeeResponse result = employeeService.getEmployeeById(employeeId);

        assertThat(result).isNotNull();
    }
}
