package com.springbootapi.exception;

import com.springbootapi.response.Meta;
import com.springbootapi.response.ResponseDTO;
import com.springbootapi.response.ResponseUtil;
import com.springbootapi.response.Status;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("Validation failed for request: {}", ex.getMessage(), ex);
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            String fieldName = fieldError.getField();
            String defaultMessage = fieldError.getDefaultMessage();
            errors.put(fieldName, defaultMessage);
        });
        log.warn("Validation errors: {}", errors);
        Meta meta = Meta.builder().code(HttpStatus.BAD_REQUEST.name()).status(Status.FAILED).description("Validation errors").build();
        var responseDto = ResponseDTO.builder().meta(meta).data(errors).build();
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(responseDto);
        return responseDto;
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

