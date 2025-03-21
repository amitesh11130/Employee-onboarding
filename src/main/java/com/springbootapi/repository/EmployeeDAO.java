package com.springbootapi.repository;

import com.springbootapi.entity.Employee;
import com.springbootapi.request.EmployeeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class EmployeeDAO {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee save(EmployeeDTO employeeDTO) {
        log.info("Received request from Employee_Service to save a new employee: {}", employeeDTO);
        Employee employee = DTOConvertor.convertUserDTO(employeeDTO);
        Employee savedEmployee = employeeRepository.save(employee);
        log.info("Successfully saved employee with ID: {}", savedEmployee.getId());
        return savedEmployee;
    }


    public List<Employee> saveAll(List<EmployeeDTO> employeeDTOList) {
        log.info("Received request from Employee_Service to save multiple employees. Number of employees to save: {}", employeeDTOList.size());
        List<Employee> employeeList = new ArrayList<>();
        for (EmployeeDTO employeeDTO : employeeDTOList) {
            Employee employee = DTOConvertor.convertUserDTO(employeeDTO);
            employeeList.add(employee);
        }
        List<Employee> savedEmployees = employeeRepository.saveAll(employeeList);
        log.info("Successfully saved {} employees.", savedEmployees.size());
        return savedEmployees;
    }

    public List<Employee> findAll() {
        log.info("Received request from Employee_Service to retrieve all employees.");
        List<Employee> employees = employeeRepository.findAll();
        log.info("Successfully retrieved {} employees.", employees.size());
        return employees;
    }

    public Employee findById(int id) {
        log.info("Received request from Employee_Service to retrieve employee with ID: {}", id);
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee == null) {
            log.warn("Employee not found with ID: {}", id);
        } else {
            log.info("Successfully retrieved employee with ID: {}", id);
        }
        return employee;
    }

    public Employee updateEmployeeName(int id, String name) {
        log.info("Received request from Employee_Service to update employee name for employee with ID: {}", id);
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));
        employee.setName(name);
        Employee updatedEmployee = employeeRepository.save(employee);
        log.info("Successfully updated name for employee with ID: {}", id);
        return updatedEmployee;
    }

    public Employee updateEmployee(int id, EmployeeDTO employeeDTO) {
        log.info("Received request from Employee_Service to update employee with ID: {}", id);
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));
        employee.setName(employeeDTO.getName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setDateOfBirth(employeeDTO.getDateOfBirth());
        employee.setContact(employeeDTO.getContact());
        Employee updatedEmployee = employeeRepository.save(employee);
        log.info("Successfully updated employee with ID: {}", id);
        return updatedEmployee;
    }

    public void deleteEmployeeById(int id) {
        log.info("Received request from Employee_Service to delete employee with ID: {}", id);
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            log.info("Successfully deleted employee with ID: {}", id);
        } else {
            log.warn("Employee not found with ID: {}", id);
        }
    }

    public boolean existById(int id) {
        log.info("Received request from Employee_Service to check if employee exists with ID: {}", id);
        boolean existsById = employeeRepository.existsById(id);
        if (existsById) {
            log.info("Employee with ID: {} exists.", id);
        } else {
            log.warn("Employee with ID: {} does not exist.", id);
        }
        return existsById;
    }
}
