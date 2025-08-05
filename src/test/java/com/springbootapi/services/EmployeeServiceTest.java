package com.springbootapi.services;

import com.springbootapi.entity.Employee;
import com.springbootapi.exception.EmployeeNotFoundException;
import com.springbootapi.repository.EmployeeDAO;
import com.springbootapi.request.EmployeeDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeDAO employeeDAO;

    @InjectMocks
    private EmployeeService employeeService;

    private EmployeeDTO employeeDTO;
    private Employee employee;


    @BeforeEach
    void setUp() {
        employeeDTO = EmployeeDTO.builder().name("Rajan").contact("9563257481").email("amit@gmail.com").dateOfBirth("02-03-1990").build();
        employee = Employee.builder().id(1).contact("9563257481").dateOfBirth("02-03-1990").name("Rajan").email("amit@gmail.com").build();
    }

    @Test
    void saveEmployee() {
        when(employeeDAO.save(any(EmployeeDTO.class))).thenReturn(employee);

        Employee savedEmployee = employeeService.saveEmployee(employeeDTO);

        assertNotNull(savedEmployee);
        verify(employeeDAO, times(1)).save(any(EmployeeDTO.class));
        assertEquals(1, savedEmployee.getId());
    }

    @Test
    void saveAllEmployee() {
        List<Employee> employeeList = Collections.singletonList(employee);
        when(employeeDAO.saveAll(anyList())).thenReturn(employeeList);

        List<Employee> employeeList1 = employeeService.saveAllEmployee(List.of(employeeDTO));
        assertNotNull(employeeList1);
        assertEquals(1, employeeList1.size());
    }

    @Test
    void getAllEmployee() {
        List<Employee> employeeList = Collections.singletonList(employee);
        when(employeeDAO.findAll()).thenReturn(employeeList);

        List<Employee> allEmployee = employeeService.getAllEmployee();
        assertNotNull(allEmployee);
        verify(employeeDAO, times(1)).findAll();
    }

    @Test
    void getEmployeeById() throws EmployeeNotFoundException {
        when(employeeDAO.existById(1)).thenReturn(true);
        when(employeeDAO.findById(1)).thenReturn(employee);

        Employee employeeById = employeeService.getEmployeeById(1);
        assertNotNull(employeeById);
        verify(employeeDAO, times(1)).findById(1);
        assertEquals(1, employeeById.getId());
    }

    @Test
    void updateEmployee() throws EmployeeNotFoundException {
        EmployeeDTO updateEmp = EmployeeDTO.builder().name("Rajesh").contact("9563257481").email("am@gmail.com").dateOfBirth("02-03-1990").build();
        Employee emp = Employee.builder().id(1).contact("9563257481").dateOfBirth("02-03-1990").name("Rajesh").email("am@gmail.com").build();
        when(employeeDAO.existById(1)).thenReturn(true);
        when(employeeDAO.updateEmployee(1, updateEmp)).thenReturn(emp);

        Employee updateEmployee = employeeService.updateEmployee(1, updateEmp);

        assertNotNull(updateEmployee);
        verify(employeeDAO, times(1)).updateEmployee(1, updateEmp);
        assertEquals("Rajesh", updateEmployee.getName());
    }

    @Test
    void updateEmployeeName() throws EmployeeNotFoundException {
        Employee updateName = Employee.builder().id(1).contact("9563257481").dateOfBirth("02-03-1990").name("Amitesh").email("am@gmail.com").build();
        when(employeeDAO.existById(1)).thenReturn(true);
        when(employeeDAO.updateEmployeeName(1, "Amitesh")).thenReturn(updateName);

        Employee updateEmployeeName = employeeService.updateEmployeeName(1, "Amitesh");

        assertNotNull(updateEmployeeName);
        verify(employeeDAO, times(1)).updateEmployeeName(1, "Amitesh");
        assertEquals("Amitesh", updateEmployeeName.getName());
    }

    @Test
    void deleteEmployeeById_Success() throws EmployeeNotFoundException {
        when(employeeDAO.existById(1)).thenReturn(true);
        employeeService.deleteEmployeeById(1);

        verify(employeeDAO, times(1)).deleteEmployeeById(1);
    }

}