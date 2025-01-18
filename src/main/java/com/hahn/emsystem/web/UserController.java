package com.hahn.emsystem.web;


import com.hahn.emsystem.dto.response.AuthenticatedUserResponse;
import com.hahn.emsystem.dto.response.EmployeeResponse;
import com.hahn.emsystem.service.EmployeeService;
import com.hahn.emsystem.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {


    private final EmployeeService employeeService;

    @GetMapping("/me")
    public AuthenticatedUserResponse getAuthenticatedUser() {

        String authenticatedUserUsername = UserUtils.getAuthenticatedUserUsername();
        EmployeeResponse employeeResponse = employeeService.getEmployeeByUsername(authenticatedUserUsername);
        return new AuthenticatedUserResponse(
                authenticatedUserUsername,
                employeeResponse.getFullName(),
                SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream( )
                        .map( authority -> authority.getAuthority() )
                        .collect( Collectors.toList() )

        );
    }
}
