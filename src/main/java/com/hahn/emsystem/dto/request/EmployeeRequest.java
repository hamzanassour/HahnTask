package com.hahn.emsystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeRequest {

    @NotBlank(message = "Full name is required.")
    private String fullName;

    @NotBlank(message = "Job title is required.")
    private String jobTitle;

    @NotBlank(message = "Department is required.")
    private String department;

    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Hire date must be in the format YYYY-MM-DD.")
    private String hireDate;

    @NotBlank(message = "Employment status is required.")
    private String employmentStatus;

    @NotBlank(message = "Contact information is required.")
    private String contactInformation;

    @NotBlank(message = "Address is required.")
    private String address;
}
