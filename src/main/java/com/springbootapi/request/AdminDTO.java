package com.springbootapi.request;

import com.springbootapi.response.Role;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminDTO {

    @NotEmpty(message = "Name is required")
    @Column(unique = true)
    private String AdminName;
    @NotEmpty(message = "Password is required")
    private String password;
    private boolean isActive;
    @Builder.Default
    @Pattern(regexp = "USER|ADMIN", message = "Role must be either USER or ADMIN")
    private String role = Role.USER.name();

}
