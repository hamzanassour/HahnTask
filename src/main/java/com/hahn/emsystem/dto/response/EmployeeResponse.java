package com.hahn.emsystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse {
    private Long id;
    private String fullName;
    private String jobTitle;
    private String department;
    private String hireDate;
    private String employmentStatus;
    private String contactInformation;
    private String address;
}
