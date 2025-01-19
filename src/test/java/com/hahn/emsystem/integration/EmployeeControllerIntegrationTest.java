package com.hahn.emsystem.integration;


import com.hahn.emsystem.dto.request.EmployeeRequest;
import com.hahn.emsystem.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmployeeService employeeService;

    private EmployeeRequest validEmployeeRequest;

    @BeforeEach
    public void setup() {
        validEmployeeRequest = new EmployeeRequest();
        validEmployeeRequest.setFullName("John Doe");
        validEmployeeRequest.setDepartment("IT");
        validEmployeeRequest.setJobTitle("Developer");
        validEmployeeRequest.setHireDate("2021-01-01");
        validEmployeeRequest.setAddress("123 Main St");
        validEmployeeRequest.setEmploymentStatus("FULL_TIME");
        validEmployeeRequest.setContactInformation("555-555-5555");
    }

    @Test
    @WithMockUser(roles = "HR")
    @Order(1)
    public void testCreateEmployee() throws Exception {
        EmployeeRequest createRequest = new EmployeeRequest();
        createRequest.setFullName("John Doe");
        createRequest.setDepartment("IT");
        createRequest.setJobTitle("Developer");
        createRequest.setHireDate("2021-01-01");
        createRequest.setAddress("123 Main St");
        createRequest.setEmploymentStatus("FULL_TIME");
        createRequest.setContactInformation("555-555-5555");

        String response = mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.fullName").value("John Doe"))
                .andExpect(jsonPath("$.department").value("IT"))
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    @WithMockUser(roles = "HR")
    @Order(2)
    public void testGetEmployeeById() throws Exception {
        mockMvc.perform(get("/api/employees/{id}", 4))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.fullName").exists());
    }

    @Test
    @WithMockUser(roles = "HR")
    @Order(3)
    public void testGetAllEmployees() throws Exception {
        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].fullName").exists());
    }

    @Test
    @WithMockUser(roles = "HR")
    @Order(4)
    public void testUpdateEmployee() throws Exception {
        EmployeeRequest updateRequest = new EmployeeRequest();
        updateRequest.setFullName("Updated Name");
        updateRequest.setDepartment("HR");
        updateRequest.setJobTitle("HR Manager");
        updateRequest.setHireDate("2021-02-01");
        updateRequest.setAddress("456 New St");
        updateRequest.setEmploymentStatus("PART_TIME");
        updateRequest.setContactInformation("555-555-5556");

        mockMvc.perform(put("/api/employees/{id}", 4)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Updated Name"))
                .andExpect(jsonPath("$.department").value("HR"));
    }

    @Test
    @WithMockUser(roles = "HR")
    @Order(5)
    public void testSearchEmployees() throws Exception {
        mockMvc.perform(get("/api/employees/search")
                        .param("searchTerm", "John")
                        .param("status", "FULL_TIME"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }


    @Test
    @WithMockUser(roles = "HR")
    @Order(6)
    public void testDeleteEmployee() throws Exception {
        mockMvc.perform(delete("/api/employees/{id}", 4))
                .andExpect(status().isNoContent());
    }
}

