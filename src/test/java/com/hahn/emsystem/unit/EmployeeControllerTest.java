package com.hahn.emsystem.unit;


import com.hahn.emsystem.dto.request.EmployeeRequest;
import com.hahn.emsystem.dto.request.ManagerUpdateEmployeeRequest;
import com.hahn.emsystem.dto.response.EmployeeResponse;
import com.hahn.emsystem.service.EmployeeService;
import com.hahn.emsystem.web.EmployeeController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

public class EmployeeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @WithMockUser(roles = "HR")
    public void testCreateEmployee() throws Exception {
        Long employeeId = 1L;
        EmployeeRequest request = new EmployeeRequest();
        request.setFullName("John");
        request.setDepartment("IT");
        request.setJobTitle("Developer");
        request.setHireDate("2021-01-01");
        request.setAddress("123 Main St");
        request.setEmploymentStatus("FULL_TIME");
        request.setContactInformation("555-555-5555");
        EmployeeResponse response = new EmployeeResponse();
        response.setId(employeeId);
        response.setFullName("John");

        when(employeeService.createEmployee(any(EmployeeRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(employeeId))
                .andExpect(jsonPath("$.fullName").value("John"));
    }

    @Test
    @WithMockUser(roles = "HR")
    public void testGetAllEmployees() throws Exception {
        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(roles = "HR")
    public void testDeleteEmployee() throws Exception {
        Long employeeId = 1L;

        doNothing().when(employeeService).deleteEmployee(employeeId);

        mockMvc.perform(delete("/api/employees/{id}", employeeId))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "HR")
    public void testUpdateEmployee() throws Exception {
        Long employeeId = 1L;
        EmployeeRequest request = new EmployeeRequest();
        request.setFullName("John");
        request.setDepartment("IT");
        request.setJobTitle("Developer");
        request.setHireDate("2021-01-01");
        request.setAddress("123 Main St");
        request.setEmploymentStatus("FULL_TIME");
        request.setContactInformation("555-555-5555");
        EmployeeResponse response = new EmployeeResponse();
        response.setId(employeeId);
        response.setFullName("John");

        when(employeeService.updateEmployee(eq(employeeId), any(EmployeeRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/employees/{id}", employeeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employeeId))
                .andExpect(jsonPath("$.fullName").value("John"));

    }

    @Test
    @WithMockUser(roles = "HR")
    public void testLimitedEmployeeUpdate() throws Exception {
        Long employeeId = 1L;
        ManagerUpdateEmployeeRequest request = new ManagerUpdateEmployeeRequest();
        request.setJobTitle("Developer");
        EmployeeResponse response = new EmployeeResponse();
        response.setId(employeeId);

        when(employeeService.limitedEmployeeUpdate(eq(employeeId), any(ManagerUpdateEmployeeRequest.class)))
                .thenReturn(response);

        mockMvc.perform(patch("/api/employees/{id}", employeeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @WithMockUser(roles = "HR")
    public void testGetEmployeeById() throws Exception {
        Long employeeId = 1L;
        EmployeeResponse response = new EmployeeResponse();
        response.setId(employeeId);

        when(employeeService.getEmployeeById(employeeId)).thenReturn(response);

        mockMvc.perform(get("/api/employees/{id}", employeeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @WithMockUser(roles = "HR")
    public void testSearchEmployees() throws Exception {
        mockMvc.perform(get("/api/employees/search")
                        .param("searchTerm", "John")
                        .param("status", "ACTIVE")
                        .param("department", "IT"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}

