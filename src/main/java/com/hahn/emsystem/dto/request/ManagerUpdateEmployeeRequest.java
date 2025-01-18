package com.hahn.emsystem.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ManagerUpdateEmployeeRequest {

    private String jobTitle;
    // TODO: add all the fields that can be updated by a manager
}
