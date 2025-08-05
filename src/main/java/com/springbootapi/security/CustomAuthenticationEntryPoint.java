package com.springbootapi.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootapi.response.Meta;
import com.springbootapi.response.ResponseDTO;
import com.springbootapi.response.Status;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.IOException;
import java.io.OutputStream;

@Component
@EnableWebMvc
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint, AccessDeniedHandler {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {

        String errorMessage = "Invalid admin name or password";

        if (authException instanceof BadCredentialsException) {
            errorMessage = "Invalid admin name or password";
        } else if (authException instanceof DisabledException) {
            errorMessage = "Account is disabled";
        } else if (authException instanceof LockedException) {
            errorMessage = "Account is locked";
        }
        Meta meta = Meta.builder()
                .code(String.valueOf(HttpStatus.UNAUTHORIZED.value()))
                .description(errorMessage)
                .status(Status.FAILED)
                .build();

        ResponseDTO responseDTO = ResponseDTO.builder().meta(meta).build();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        OutputStream outputStream = response.getOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(outputStream, responseDTO);
        outputStream.flush();
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Meta meta = Meta.builder()
                .code(String.valueOf(HttpStatus.UNAUTHORIZED.value()))
                .description("Access Denied: You do not have permission to access this resource.")
                .status(Status.FAILED)
                .build();

        ResponseDTO responseDTO = ResponseDTO.builder().meta(meta).build();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        OutputStream outputStream = response.getOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(outputStream, responseDTO);
        outputStream.flush();
    }
}
