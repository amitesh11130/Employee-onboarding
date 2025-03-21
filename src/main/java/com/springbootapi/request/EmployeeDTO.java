package com.springbootapi.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {


    @NotEmpty(message = "Name is required")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z ]+$",message = "Name can only contain alphabetic characters and spaces")
    private String name;

    @Pattern(regexp = "^(?:\\+91|91)?[789]\\d{9}$", message = "Contact must be a 10||12-digit number")
    @NotEmpty(message = "Contact is required")
    private String contact;

    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email is required")
    private String email;

    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(19|20)\\d\\d$", message = "Date of Birth must be in DD-MM-YYYY format")
    @NotEmpty(message = "DateOfBirth is required")
    private String dateOfBirth;


}
