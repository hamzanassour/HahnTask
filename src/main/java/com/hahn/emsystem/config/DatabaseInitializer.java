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
        jdbcTemplate.update("INSERT INTO role (name) VALUES ('EMPLOYEE')");

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


        jdbcTemplate.update(
                "INSERT INTO employee (username, password, full_name, job_title, department, hire_date, employment_status, contact_information, address) " +
                        "VALUES ('employee_1', ?, 'Alice Green', 'Employee', 'Sales', '2023-01-10', 'Active', 'alice.green@example.com', '101 Maple Street, Springfield, USA')",
                adminPassword
        );

        jdbcTemplate.update(
                "INSERT INTO employee (username, password, full_name, job_title, department, hire_date, employment_status, contact_information, address) " +
                        "VALUES ('employee_2', ?, 'Bob White', 'Employee', 'Marketing', '2023-01-15', 'Inactive', 'bob.white@example.com', '202 Birch Street, Springfield, USA')",
                adminPassword
        );

        jdbcTemplate.update(
                "INSERT INTO employee (username, password, full_name, job_title, department, hire_date, employment_status, contact_information, address) " +
                        "VALUES ('employee_3', ?, 'Charlie Blue', 'Employee', 'HR', '2023-01-20', 'On Leave', 'charlie.blue@example.com', '303 Pine Street, Springfield, USA')",
                adminPassword
        );

        jdbcTemplate.update(
                "INSERT INTO employee (username, password, full_name, job_title, department, hire_date, employment_status, contact_information, address) " +
                        "VALUES ('employee_4', ?, 'David Black', 'Employee', 'Finance', '2023-01-25', 'Active', 'david.black@example.com', '404 Cedar Street, Springfield, USA')",
                adminPassword
        );

        jdbcTemplate.update(
                "INSERT INTO employee (username, password, full_name, job_title, department, hire_date, employment_status, contact_information, address) " +
                        "VALUES ('employee_5', ?, 'Eva Red', 'Employee', 'IT', '2023-01-30', 'Inactive', 'eva.red@example.com', '505 Willow Street, Springfield, USA')",
                adminPassword
        );

        jdbcTemplate.update(
                "INSERT INTO employee (username, password, full_name, job_title, department, hire_date, employment_status, contact_information, address) " +
                        "VALUES ('employee_6', ?, 'Frank Yellow', 'Employee', 'Sales', '2023-02-01', 'On Leave', 'frank.yellow@example.com', '606 Cherry Street, Springfield, USA')",
                adminPassword
        );

        // assign roles to employees
        jdbcTemplate.update("INSERT INTO employee_roles (employee_id, roles_id) VALUES (4, 4)"); // Employee
        jdbcTemplate.update("INSERT INTO employee_roles (employee_id, roles_id) VALUES (5, 4)"); // Employee
        jdbcTemplate.update("INSERT INTO employee_roles (employee_id, roles_id) VALUES (6, 4)"); // Employee
        jdbcTemplate.update("INSERT INTO employee_roles (employee_id, roles_id) VALUES (7, 4)"); // Employee
        jdbcTemplate.update("INSERT INTO employee_roles (employee_id, roles_id) VALUES (8, 4)"); // Employee
        jdbcTemplate.update("INSERT INTO employee_roles (employee_id, roles_id) VALUES (9, 4)"); // Employee



        System.out.println("Database has been initialized with roles and detailed employee information.");
    }
}
