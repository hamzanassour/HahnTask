package com.hahn.emsystem.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeRequest {
    private String fullName;
    private String jobTitle;
    private String department;
    private String hireDate;
    private String employmentStatus;
    private String contactInformation;
    private String address;
}
