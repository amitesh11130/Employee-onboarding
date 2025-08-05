package com.springbootapi.controller;

import com.springbootapi.entity.Employee;
import com.springbootapi.request.EmployeeDTO;
import com.springbootapi.response.ResponseDTO;
import com.springbootapi.services.EmployeeService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private EmployeeDTO employeeDTO;
    private Employee employee;

    @BeforeEach
    public void setUp() {
        employeeDTO = EmployeeDTO.builder().name("ShivaJi Chandra").email("shiva11120@gmail.com").contact("8456214783").dateOfBirth("12-10-1996").build();
        employee = Employee.builder().id(1).name("ShivaJi Chandra").email("shiva11120@gmail.com").contact("8456214783").dateOfBirth("12-10-1996").build();
    }

    @Test
    void saveEmployee() {

        when(employeeService.saveEmployee(employeeDTO)).thenReturn(employee);

        ResponseEntity<ResponseDTO> response = employeeController.saveEmployee(employeeDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Employee created successfully", response.getBody().getMeta().getDescription());
    }

    @Test
    void saveAllEmployee() {
        when(employeeService.saveAllEmployee(List.of(employeeDTO))).thenReturn(List.of(employee));

        ResponseEntity<ResponseDTO> response = employeeController.saveAllEmployee(List.of(employeeDTO));

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Employee created successfully", response.getBody().getMeta().getDescription());

    }

    @Test
    void getAllEmployee() {
    }

    @Test
    void getEmployeeById() {
    }

    @Test
    void updateEmployee() {
    }

    @Test
    void updateEmployeeName() {
    }

    @Test
    void deleteEmployee() {
    }
}