package com.hahn.emsystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EmployeeManagerResponse {
    private Long id;
    private String fullName;
    private String jobTitle;
    private String department;
    private String hireDate;
}
