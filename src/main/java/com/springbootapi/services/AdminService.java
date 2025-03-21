package com.springbootapi.services;

import com.springbootapi.entity.Admin;
import com.springbootapi.execption.AdminNotFoundException;
import com.springbootapi.repository.DTOConvertor;
import com.springbootapi.repository.AdminRepository;
import com.springbootapi.request.AdminDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    public Admin getAdminById(Integer id) throws AdminNotFoundException {
        log.info("Request received from Controller to retrieve admin with ID: {}", id);
        boolean existsById = adminRepository.existsById(id);
        if (existsById) {
            Admin admin = adminRepository.findById(id).get();
            maskPassword(List.of(admin));
            log.info("Successfully retrieved admin with ID: {}", admin.getId());
            return admin;
        }
        log.warn("Admin not found with ID: {}", id);
        throw new AdminNotFoundException("Admin not found with given id !! " + id);
    }

    public void deleteAdminById(Integer id) throws AdminNotFoundException {
        log.info("Request received from Controller to delete admin with ID: {}", id);
        boolean existsById = adminRepository.existsById(id);
        if (existsById) {
            adminRepository.deleteById(id);
            log.info("Successfully delete admin with ID: {}", id);
            return;
        }
        log.warn("Admin not found with ID: {}", id);
        throw new AdminNotFoundException("Admin not found with given id !! " + id);
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
        Admin admin = adminRepository.findById(id).get();
        admin.setAdminName(adminDTO.getAdminName());
        admin.setPassword(adminDTO.getPassword());
        admin.setRole(adminDTO.getRole());
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        Admin updatedAdmin = adminRepository.save(admin);
        maskPassword(List.of(updatedAdmin));
        log.info("Successfully updated admin with ID: {}", updatedAdmin.getId());
        return updatedAdmin;
    }
}
