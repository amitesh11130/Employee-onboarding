package com.springbootapi.controller;

import com.springbootapi.entity.Employee;
import com.springbootapi.exception.EmployeeNotFoundException;
import com.springbootapi.request.EmployeeDTO;
import com.springbootapi.services.EmployeeService;
import com.springbootapi.response.ResponseDTO;
import com.springbootapi.response.ResponseUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Validated
@RestController
@Slf4j
@RequestMapping(value = "/api/v1/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping(value = "/save")
    public ResponseEntity<ResponseDTO> saveEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        log.info("Received request to save new employee");
        Employee savedEmployee = employeeService.saveEmployee(employeeDTO);
        log.info("Successfully saved employee with ID {}", savedEmployee.getId());
        return ResponseUtil.created("Employee created successfully", savedEmployee);
    }

    @PostMapping(value = "/saveAll")
    public ResponseEntity<ResponseDTO> saveAllEmployee(@RequestBody @Valid List<EmployeeDTO> employeeDTO) {
        log.info("Received request to save multiple employee");
        List<Employee> savedEmployee = employeeService.saveAllEmployee(employeeDTO);
        log.info("Successfully saved {} employee", savedEmployee.size());
        return ResponseUtil.created("Employee created successfully", savedEmployee);
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<ResponseDTO> getAllEmployee() {
        log.info("Received request to retrieved all employee");
        List<Employee> employees = employeeService.getAllEmployee();
        if (ObjectUtils.isEmpty(employees)) {
            log.warn("No employee found in the database");
            return ResponseUtil.notFound("No Employees found");
        }
        log.info("Successfully retrieved {} employee", employees.size());
        return ResponseUtil.success("Employees retrieved successfully", employees);
    }

    @GetMapping(value = "/getById")
    public ResponseEntity<ResponseDTO> getEmployeeById(@RequestHeader("id") int id) throws EmployeeNotFoundException {
        log.info("Received request to retrieved employee");
        Employee employee = employeeService.getEmployeeById(id);
        if (ObjectUtils.isEmpty(employee)) {
            log.warn("No employee found in the database");
            return ResponseUtil.notFound("No Employee found");
        }
        log.info("Successfully retrieved employee with ID {}", id);
        return ResponseUtil.success("Employee retrieved successfully", employee);

    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<ResponseDTO> updateEmployee(@PathVariable int id,
                                                      @Valid @RequestBody EmployeeDTO employeeDTO) throws EmployeeNotFoundException {
        log.info("Received request to update employee with ID {}", id);
        Employee updatedEmployee = employeeService.updateEmployee(id, employeeDTO);
        log.info("Successfully updated employee with ID {}", id);
        return ResponseUtil.updated("Employee updated successfully", updatedEmployee);

    }

    @PutMapping(value = "/update/{id}/{name}")
    public ResponseEntity<ResponseDTO> updateEmployeeName(@PathVariable int id,
                                                          @PathVariable String name) throws EmployeeNotFoundException {
        log.info("Received request to update employee name with ID {}", id);
        Employee updatedEmployee = employeeService.updateEmployeeName(id, name);
        log.info("Successfully updated employee name with ID {}", id);
        return ResponseUtil.updated("Employee name updated successfully", updatedEmployee);

    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<ResponseDTO> deleteEmployee(@RequestParam("id") int id) throws EmployeeNotFoundException {
        log.info("Received request to remove employee with ID {}", id);
        employeeService.deleteEmployeeById(id);
        log.info("Successfully removed employee with ID {}", id);
        return ResponseUtil.deleted("Employee deleted successfully", true);

    }
}