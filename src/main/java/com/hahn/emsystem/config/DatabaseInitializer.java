package com.hahn.emsystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) throws Exception {
        // Insert roles
        jdbcTemplate.update("INSERT INTO role (name) VALUES ('MANAGER')");
        jdbcTemplate.update("INSERT INTO role (name) VALUES ('HR')");
        jdbcTemplate.update("INSERT INTO role (name) VALUES ('ADMIN')");

        // Encode passwords
        String managerPassword = passwordEncoder.encode("password123");
        String hrPassword = passwordEncoder.encode("password123");
        String adminPassword = passwordEncoder.encode("password123");

        // Insert employees with detailed information
        jdbcTemplate.update(
                "INSERT INTO employee (username, password, full_name, job_title, department, hire_date, employment_status, contact_information, address) " +
                        "VALUES ('manager_user', ?, 'Jane Smith', 'Department Manager', 'Sales', '2022-03-15', 'Full-Time', 'jane.smith@example.com', '456 Elm Street, Springfield, USA')",
                managerPassword
        );

        jdbcTemplate.update(
                "INSERT INTO employee (username, password, full_name, job_title, department, hire_date, employment_status, contact_information, address) " +
                        "VALUES ('hr_user', ?, 'Emily Johnson', 'HR Specialist', 'Human Resources', '2021-07-01', 'Full-Time', 'emily.johnson@example.com', '789 Oak Street, Springfield, USA')",
                hrPassword
        );

        jdbcTemplate.update(
                "INSERT INTO employee (username, password, full_name, job_title, department, hire_date, employment_status, contact_information, address) " +
                        "VALUES ('admin_user', ?, 'Michael Brown', 'System Administrator', 'IT', '2020-05-01', 'Full-Time', 'michael.brown@example.com', '123 Main Street, Springfield, USA')",
                adminPassword
        );

        // Assign roles to employees
        jdbcTemplate.update("INSERT INTO employee_roles (employee_id, roles_id) VALUES (1, 1)"); // Manager
        jdbcTemplate.update("INSERT INTO employee_roles (employee_id, roles_id) VALUES (2, 2)"); // HR
        jdbcTemplate.update("INSERT INTO employee_roles (employee_id, roles_id) VALUES (3, 3)"); // Admin

        System.out.println("Database has been initialized with roles and detailed employee information.");
    }
}
