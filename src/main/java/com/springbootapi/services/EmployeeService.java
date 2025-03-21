package com.springbootapi.services;

import com.springbootapi.entity.Employee;
import com.springbootapi.execption.EmployeeNotFoundException;
import com.springbootapi.repository.EmployeeDAO;
import com.springbootapi.request.EmployeeDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class EmployeeService {

    @Autowired
    private EmployeeDAO employeeDAO;


    public Employee saveEmployee(EmployeeDTO employeeDTO) {
        log.info("Request received from Controller to save a new employee: {}", employeeDTO);
        Employee saveEmployee = employeeDAO.save(employeeDTO);
        log.info("Successfully saved employee with ID: {}", saveEmployee.getId());
        return saveEmployee;
    }

    public List<Employee> saveAllEmployee(List<EmployeeDTO> employeeDTOList) {
        log.info("Request received from Controller to save multiple employee: Number of employees to save: {}", employeeDTOList.size());
        List<Employee> employeeList = employeeDAO.saveAll(employeeDTOList);
        log.info("Successfully saved {} employee", employeeList.size());
        return employeeList;
    }

    public List<Employee> getAllEmployee() {
        log.info("Request received from Controller to retrieve all employee");
        List<Employee> employeeList = employeeDAO.findAll();
        log.info("Successfully retrieve {} employee", employeeList.size());
        return employeeList;
    }


    public Employee getEmployeeById(int id) throws EmployeeNotFoundException {
        log.info("Request received from Controller to retrieve employee with ID: {}", id);
        boolean exist = employeeDAO.existById(id);
        if (exist) {
            Employee employee = employeeDAO.findById(id);
            log.info("Successfully retrieve employee with ID: {}", employee.getId());
            return employee;
        }
        log.warn("Employee not found with ID: {}", id);
        throw new EmployeeNotFoundException("Employee not found with given id !! " + id);
    }

    public Employee updateEmployee(int id, EmployeeDTO employeeDTO) throws EmployeeNotFoundException {
        log.info("Request received from Controller to update employee with ID: {}", id);
        boolean exist = employeeDAO.existById(id);
        if (exist) {
            Employee updateEmployee = employeeDAO.updateEmployee(id, employeeDTO);
            log.info("Successfully updated employee with ID: {}", updateEmployee.getId());
            return updateEmployee;
        }
        log.warn("Employee not found with ID: {}", id);
        throw new EmployeeNotFoundException("No Employee found to update with given id !! " + id);
    }

    public Employee updateEmployeeName(int id, String name) throws EmployeeNotFoundException {
        log.info("Request received from Controller to update employee name with ID: {}", id);
        boolean exist = employeeDAO.existById(id);
        if (exist) {
            Employee updateEmployeeName = employeeDAO.updateEmployeeName(id, name);
            log.info("Successfully updated employee name with ID: {}", updateEmployeeName.getId());
            return updateEmployeeName;
        }
        log.warn("Employee not found with ID: {}", id);
        throw new EmployeeNotFoundException("No Employee found to update name with given id !! " + id);
    }

    public void deleteEmployeeById(int id) throws EmployeeNotFoundException {
        log.info("Request received from Controller to delete employee with ID: {}", id);
        boolean user = employeeDAO.existById(id);
        if (user) {
            employeeDAO.deleteEmployeeById(id);
            log.info("Successfully deleted employee with ID: {}", id);
            return;
        }
        log.warn("Employee not found with ID: {}", id);
        throw new EmployeeNotFoundException("No Employee found to delete with given id !! " + id);
    }

//    public boolean existById(int id) {
//        return employeeDAO.existById(id);
//    }
}
