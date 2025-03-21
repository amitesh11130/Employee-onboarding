package com.springbootapi.execption;

import com.springbootapi.response.ResponseDTO;
import com.springbootapi.response.ResponseUtil;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("Validation failed for request: {}", ex.getMessage(), ex);
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            String fieldName = fieldError.getField();
            String defaultMessage = fieldError.getDefaultMessage();
            errors.put(fieldName, defaultMessage);
        });
        log.info("Validation errors: {}", errors);
        return ResponseUtil.failed(errors.toString());
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseDTO> employeeNotFoundException(EmployeeNotFoundException e) {
        log.warn("Employee not found: {}", e.getMessage(), e);
        return ResponseUtil.failed(e.getMessage());
    }

    @ExceptionHandler(AdminNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseDTO> adminNotFoundException(AdminNotFoundException e) {
        log.warn("Admin not found: {}", e.getMessage(), e);
        return ResponseUtil.failed(e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ResponseDTO> handleConstraintViolationException(ConstraintViolationException ex) {
        log.error("Constraint violation occurred: {}", ex.getMessage(), ex);
        Map<String, String> errors = new HashMap<>();

        ex.getConstraintViolations().forEach(violation ->
                errors.put(violation.getPropertyPath().toString(), violation.getMessage())
        );
        log.info("Constraint violations: {}", errors);
        return ResponseUtil.failed(errors.toString());
    }

}

