package com.springbootapi.services;

import com.springbootapi.entity.Admin;
import com.springbootapi.exception.AdminNotFoundException;
import com.springbootapi.repository.DTOConvertor;
import com.springbootapi.repository.AdminRepository;
import com.springbootapi.request.AdminDTO;
import jakarta.validation.constraints.NotEmpty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AdminService {


    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public Admin saveAdmin(AdminDTO adminDTO) {
        log.info("Request received from Controller to save a new admin: {}", adminDTO);
        Admin admin = DTOConvertor.convertUserAccessDTO(adminDTO);
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        Admin savedAdmin = adminRepository.save(admin);

        maskPassword(List.of(savedAdmin));
        log.info("Successfully saved admin with ID: {}", savedAdmin.getId());
        return savedAdmin;
    }


    public List<Admin> saveAllAdmin(List<AdminDTO> adminDTOList) {
        log.info("Request received from Controller to save multiple admins. Number of admins to save: {}", adminDTOList.size());
        List<Admin> adminList = new ArrayList<>();
        for (AdminDTO adminDTO1 : adminDTOList) {
            Admin admin = DTOConvertor.convertUserAccessDTO(adminDTO1);
            admin.setPassword(passwordEncoder.encode(admin.getPassword()));
            adminList.add(admin);
        }
        List<Admin> admins = adminRepository.saveAll(adminList);
        maskPassword(admins);
        log.info("Successfully saved {} admin", admins.size());
        return admins;
    }

    public List<Admin> getAllAdmin() {
        log.info("Request received from Controller to retrieve all admins");
        List<Admin> admins = adminRepository.findAll();
        maskPassword(admins);
        log.info("Successfully retrieved {} admins", admins.size());
        return admins;
    }

    public Admin getAdminById(Integer id) {
        log.info("Request received from Controller to retrieve admin with ID: {}", id);
        Optional<Admin> adminById = adminRepository.findById(id);
        if (adminById.isPresent()) {
            Admin admin = adminById.get();
            maskPassword(List.of(admin));
            log.info("Successfully retrieved admin with ID: {}", admin.getId());
            return admin;
        }
        try {
            log.warn("Admin not found to retrieve admin with ID: {} ", id);
            throw new AdminNotFoundException("Admin not found with given id !! " + id);
        } catch (AdminNotFoundException e) {
            return null;
        }
    }

    public boolean deleteAdminById(Integer id) {
        log.info("Request received from Controller to delete admin with ID: {}", id);
        boolean existsById = adminRepository.existsById(id);
        if (existsById) {
            adminRepository.deleteById(id);
            log.info("Successfully delete admin with ID: {}", id);
            return true;
        }
        log.warn("Admin not found with ID: {}", id);
        try {
            throw new AdminNotFoundException("Admin not found with given id !! " + id);
        } catch (AdminNotFoundException e) {
            return false;
        }
    }

    public void maskPassword(List<Admin> adminList) {
        if (adminList == null) {
            return;
        }
        for (Admin admin : adminList)
            admin.setPassword("*********");
    }

    public Admin updateAdmin(Integer id, AdminDTO adminDTO) {
        log.info("Request received from Controller to update admin with ID: {}", id);
        Optional<Admin> byId = adminRepository.findById(id);
        if (byId.isPresent()) {
            byId.get().setAdminName(adminDTO.getAdminName());
            byId.get().setPassword(adminDTO.getPassword());
            byId.get().setRole(adminDTO.getRole());
            byId.get().setPassword(passwordEncoder.encode(byId.get().getPassword()));
            Admin updatedAdmin = adminRepository.save(byId.get());
            maskPassword(List.of(updatedAdmin));
            log.info("Successfully updated admin with ID: {}", updatedAdmin.getId());
            return updatedAdmin;
        }
        log.warn("Failed to update admin because admin not found with ID: {}", id);
        return null;
    }

    public Admin findByAdminName(@NotEmpty(message = "Name is required") String adminName) {
        log.info("Request received from Controller to find admin present or not With NAME: {}", adminName);
        return adminRepository.findByAdminName(adminName);
    }
}
