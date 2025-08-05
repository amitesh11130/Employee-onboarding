package com.springbootapi.services;

import com.springbootapi.entity.Admin;
import com.springbootapi.repository.AdminRepository;
import com.springbootapi.request.AdminDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

@ExtendWith(SpringExtension.class)
public class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AdminService adminService;

    private AdminDTO adminDTO;
    private Admin admin;

    @BeforeEach
    public void setup() {
        adminDTO = new AdminDTO();
        adminDTO.setAdminName("adminName");
        adminDTO.setPassword("password123");
        adminDTO.setRole("ADMIN");

        admin = new Admin();
        admin.setId(1);
        admin.setAdminName("adminName");
        admin.setPassword("password123");
        admin.setRole("ADMIN");
    }

    @Test
    public void testSaveAdmin() {
        when(passwordEncoder.encode(admin.getPassword())).thenReturn("*********");
        when(adminRepository.save(any(Admin.class))).thenReturn(admin);

        Admin savedAdmin = adminService.saveAdmin(adminDTO);

        assertNotNull(savedAdmin);
        assertEquals("*********", savedAdmin.getPassword());
        verify(adminRepository, times(1)).save(any(Admin.class));
    }

    @Test
    public void testGetAllAdmin() {
        List<Admin> adminList = Arrays.asList(admin);
        when(adminRepository.findAll()).thenReturn(adminList);

        List<Admin> admins = adminService.getAllAdmin();

        assertEquals(1, admins.size());
        verify(adminRepository, times(1)).findAll();
    }

    @Test
    public void testGetAdminById_Success() {
        when(adminRepository.findById(1)).thenReturn(Optional.of(admin));

        Admin fetchedAdmin = adminService.getAdminById(1);

        assertNotNull(fetchedAdmin);
        assertEquals(1, fetchedAdmin.getId());
    }

    @Test
    public void testGetAdminById_Failure() {
        when(adminRepository.findById(1)).thenReturn(Optional.empty());

        Admin adminById = adminService.getAdminById(1);
        assertNull(adminById);
    }

    @Test
    public void testDeleteAdminById_Success() {
        when(adminRepository.existsById(1)).thenReturn(true);

        adminService.deleteAdminById(1);

        verify(adminRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteAdminById_Failure() {
        when(adminRepository.existsById(1)).thenReturn(false);

        boolean b = adminService.deleteAdminById(1);

        assertFalse(b);
    }

    @Test
    public void testUpdateAdmin_Success() {
        when(adminRepository.findById(1)).thenReturn(Optional.of(admin));
        when(adminRepository.save(any(Admin.class))).thenReturn(admin);

        AdminDTO updatedDTO = new AdminDTO();
        updatedDTO.setAdminName("newAdminName");
        updatedDTO.setPassword("newPassword");
        updatedDTO.setRole("ROLE_USER");

        Admin updatedAdmin = adminService.updateAdmin(1, updatedDTO);

        assertNotNull(updatedAdmin);
        assertEquals("newAdminName", updatedAdmin.getAdminName());
    }

    @Test
    public void testUpdateAdmin_Failure() {
        when(adminRepository.findById(1)).thenReturn(Optional.empty());

        AdminDTO updatedDTO = new AdminDTO();
        updatedDTO.setAdminName("newAdminName");
        updatedDTO.setPassword("newPassword");
        updatedDTO.setRole("ROLE_USER");

        Admin updatedAdmin = adminService.updateAdmin(1, updatedDTO);

        assertNull(updatedAdmin);
    }

    @Test
    public void testFindByAdminName() {
        when(adminRepository.findByAdminName("adminName")).thenReturn(admin);

        Admin foundAdmin = adminService.findByAdminName("adminName");

        assertNotNull(foundAdmin);
        assertEquals("adminName", foundAdmin.getAdminName());
    }
}
