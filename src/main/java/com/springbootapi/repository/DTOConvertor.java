package com.springbootapi.repository;

import com.springbootapi.entity.Employee;
import com.springbootapi.entity.Admin;
import com.springbootapi.request.AdminDTO;
import com.springbootapi.request.EmployeeDTO;

public class DTOConvertor {

    public static Employee convertUserDTO(EmployeeDTO employeeDTO) {

        return Employee.builder()
                .name(employeeDTO.getName())
                .email(employeeDTO.getEmail())
                .contact(employeeDTO.getContact())
                .dateOfBirth(employeeDTO.getDateOfBirth())
                .build();
    }

    public static Admin convertUserAccessDTO(AdminDTO adminDTO) {

        return Admin.builder()
                .adminName(adminDTO.getAdminName())
                .password(adminDTO.getPassword())
                .isActive(adminDTO.isActive())
                .role(adminDTO.getRole())
                .build();
    }
}
