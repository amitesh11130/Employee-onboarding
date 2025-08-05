package com.springbootapi.controller;

import com.springbootapi.entity.Admin;
import com.springbootapi.request.AdminDTO;
import com.springbootapi.response.ResponseDTO;
import com.springbootapi.services.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class AdminControllerTest {

    @Mock
    private AdminService adminService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AdminController adminController;

    private AdminDTO adminDTO;
    private Admin admin;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // Initialize mock data
        adminDTO = new AdminDTO("adminName", "password", true, "USER");
        admin = new Admin(1, "adminName", "password", true, "USER");
    }

    @Test
    public void testSaveAdmin_Success() {
        when(adminService.findByAdminName(adminDTO.getAdminName())).thenReturn(null);  // No existing admin
        when(adminService.saveAdmin(adminDTO)).thenReturn(admin);
        when(passwordEncoder.encode(adminDTO.getPassword())).thenReturn("*********");

        ResponseEntity<ResponseDTO> response = adminController.saveAdmin(adminDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Admin created successfully", response.getBody().getMeta().getDescription());
    }

    @Test
    public void testSaveAdmin_AdminAlreadyExists() {
        when(adminService.findByAdminName(adminDTO.getAdminName())).thenReturn(admin);  // Admin exists

        ResponseEntity<ResponseDTO> response = adminController.saveAdmin(adminDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Failed to create admin because admin already present!", response.getBody().getMeta().getDescription());
    }

    @Test
    public void testGetAllAdmin_Success() {
        when(adminService.getAllAdmin()).thenReturn(List.of(admin));

        ResponseEntity<ResponseDTO> response = adminController.getAllAdmin();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Admin retrieved successfully", response.getBody().getMeta().getDescription());
    }

    @Test
    public void testGetAllAdmin_NoAdminsFound() {
        when(adminService.getAllAdmin()).thenReturn(List.of());

        ResponseEntity<ResponseDTO> response = adminController.getAllAdmin();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No admin found", response.getBody().getMeta().getDescription());
    }

    @Test
    public void testGetAdminById_Success() {
        when(adminService.getAdminById(1)).thenReturn(admin);

        ResponseEntity<ResponseDTO> response = adminController.getAdminById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Admin retrieved successfully", response.getBody().getMeta().getDescription());
    }

    @Test
    public void testGetAdminById_AdminNotFound() {
        when(adminService.getAdminById(999)).thenReturn(null);

        ResponseEntity<ResponseDTO> response = adminController.getAdminById(999);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Admin not found with ID: " + 999, response.getBody().getMeta().getDescription());
    }

    @Test
    public void testUpdateAdmin_Success() {
        when(adminService.updateAdmin(1, adminDTO)).thenReturn(admin);
        when(passwordEncoder.encode(adminDTO.getPassword())).thenReturn("encodedPassword");

        ResponseEntity<ResponseDTO> response = adminController.updateAdmin(1, adminDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Admin updated successfully", response.getBody().getMeta().getDescription());
    }

    @Test
    public void testUpdateAdmin_AdminNotFound() {
        when(adminService.updateAdmin(1, adminDTO)).thenReturn(null);

        ResponseEntity<ResponseDTO> response = adminController.updateAdmin(1, adminDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("No admins found to update", response.getBody().getMeta().getDescription());
    }

    @Test
    public void testDeleteAdmin_Success() {
        when(adminService.deleteAdminById(1)).thenReturn(true);

        ResponseEntity<ResponseDTO> response = adminController.deleteAdmin(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Admin deleted successfully", response.getBody().getMeta().getDescription());
    }

    @Test
    public void testDeleteAdmin_AdminNotFound() {
        when(adminService.deleteAdminById(999)).thenReturn(false);

        ResponseEntity<ResponseDTO> response = adminController.deleteAdmin(999);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Admin not found with ID: " + 999, response.getBody().getMeta().getDescription());
    }
}
