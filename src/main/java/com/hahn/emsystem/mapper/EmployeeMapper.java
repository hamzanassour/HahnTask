package com.hahn.emsystem.mapper;

import com.hahn.emsystem.dto.request.EmployeeRequest;
import com.hahn.emsystem.dto.request.ManagerUpdateEmployeeRequest;
import com.hahn.emsystem.dto.response.EmployeeManagerResponse;
import com.hahn.emsystem.dto.response.EmployeeResponse;
import com.hahn.emsystem.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    Employee toEntity(EmployeeRequest request);

    EmployeeResponse toResponse(Employee employee);

    EmployeeManagerResponse toManagerResponse(Employee employee);

    List<EmployeeResponse> toResponseList(List<Employee> employees);

    List<EmployeeManagerResponse> toManagerResponseList(List<Employee> employees);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromRequest(ManagerUpdateEmployeeRequest request, @MappingTarget Employee employee);
}
