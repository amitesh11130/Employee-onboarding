package com.springbootapi.validation;

import com.springbootapi.response.ResponseDTOError;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DTOValidation {

    public static ResponseDTOError validate(BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            String defaultMessage = fieldErrors.stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining(", "));

            String objectName = fieldErrors.isEmpty() ? "" : fieldErrors.get(0).getObjectName();
            String field = fieldErrors.isEmpty() ? "" : fieldErrors.get(0).getField();
            String rejectedValue = fieldErrors.isEmpty() ? "" : fieldErrors.get(0).getRejectedValue() != null ? Objects.requireNonNull(fieldErrors.get(0).getRejectedValue()).toString() : "";
            return new ResponseDTOError(defaultMessage, objectName, field, rejectedValue);
        }
        return new ResponseDTOError("Validation passed", " ", "", "");
    }
}
