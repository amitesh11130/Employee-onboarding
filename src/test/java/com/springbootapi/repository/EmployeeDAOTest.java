package com.springbootapi.repository;

import com.springbootapi.entity.Employee;
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
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class EmployeeDAOTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeDAO employeeDAO;

    private EmployeeDTO employeeDTO;
    private Employee employee;

    @BeforeEach
    void setUp() {
        employeeDTO = EmployeeDTO.builder().name("Rajan").contact("9563257481").email("amit@gmail.com").dateOfBirth("02-03-1990").build();
        employee = Employee.builder().id(1).contact("9563257481").dateOfBirth("02-03-1990").name("Rajan").email("amit@gmail.com").build();
    }

    @Test
    void save() {
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        Employee save = employeeDAO.save(employeeDTO);
        assertNotNull(save);
        assertEquals("Rajan", save.getName());
    }

    @Test
    void saveAll() {
        List<Employee> employeeList = Collections.singletonList(employee);
        when(employeeRepository.saveAll(anyList())).thenReturn(employeeList);

        List<Employee> employeeList1 = employeeDAO.saveAll(List.of(employeeDTO));
        assertNotNull(employeeList1);
        assertEquals(1, employeeList1.size());
    }

    @Test
    void findAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void updateEmployeeName() {
    }

    @Test
    void updateEmployee() {
    }

    @Test
    void deleteEmployeeById() {
    }

    @Test
    void existById() {
    }
}