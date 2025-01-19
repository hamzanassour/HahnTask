package com.hahn.emsystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ManagerUpdateEmployeeRequest {

    @NotBlank(message = "Job title is required.")
    private String jobTitle;
    // TODO i chose only job title to be updated, but we can add more fields to be updated
}
