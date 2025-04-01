package com.springbootapi.controller;


import com.springbootapi.entity.Admin;
import com.springbootapi.execption.AdminNotFoundException;
import com.springbootapi.request.AdminDTO;
import com.springbootapi.response.ResponseDTO;
import com.springbootapi.response.ResponseUtil;
import com.springbootapi.services.AdminService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@Slf4j
@RequestMapping(("/api/v1/admin"))
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping(value = "/save")
    public ResponseEntity<ResponseDTO> saveAdmin(@Valid @RequestBody AdminDTO adminDTO) {
        log.info("Received request to save a new admin ");
        Admin savedAdmin = adminService.saveAdmin(adminDTO);
        log.info("Successfully saved admin with ID: {}", savedAdmin.getId());
        return ResponseUtil.created("Admin created successfully", savedAdmin);
    }

    @PostMapping(value = "/saveAll")
    public ResponseEntity<ResponseDTO> saveAllAdmin(@Valid @RequestBody List<AdminDTO> adminDTO) {
        log.info("Received request to save multiple admins");
        List<Admin> saveAllAdmin = adminService.saveAllAdmin(adminDTO);
        log.info("Successfully saved {} admins", saveAllAdmin.size());
        return ResponseUtil.created("Admin created successfully", saveAllAdmin.size());
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<ResponseDTO> getAllAdmin() {
        log.info("Received request to retrieve all admins");
        List<Admin> admins = adminService.getAllAdmin();

        if (ObjectUtils.isEmpty(admins)) {
            log.warn("No admins found in the database");
            return ResponseUtil.notFound("No admin found");
        }
        log.info("Successfully retrieved {} admins", admins.size());
        return ResponseUtil.success("Admin retrieved successfully", admins);
    }

    @GetMapping(value = "/getById")
    public ResponseEntity<ResponseDTO> getAdminById(@RequestHeader("id") Integer id) throws AdminNotFoundException {
        log.info("Received request to retrieve admin by ID: {}", id);
        if (id == null || id <= 0) {
            log.warn("Invalid ID provided: {}", id);
            return ResponseUtil.error("Invalid ID provided");
        }
        Admin admin = adminService.getAdminById(id);
        log.info("Successfully retrieved admin with ID: {}", admin.getId());
        return ResponseUtil.success("Admin retrieved successfully", admin);
    }

    @PutMapping(value = "/updateAdmin/{id}")
    public ResponseEntity<ResponseDTO> updateAdmin(@PathVariable Integer id,
                                                   @Valid @RequestBody AdminDTO adminDTO) throws AdminNotFoundException {
        log.info("Received request to update admin with ID: {}", id);
        if (id == null || id <= 0) {
            log.warn("Invalid ID provided: {}", id);
            return ResponseUtil.error("Invalid ID provided");
        }
        Admin updatedAdmin = adminService.updateAdmin(id, adminDTO);
        if (updatedAdmin == null) {
            log.warn("No admins found to update with given ID: {}", id);
            return ResponseUtil.failed("No admins found to update");
        }
        log.info("Successfully update admin with ID: {}", updatedAdmin.getId());
        return ResponseUtil.updated("Admin updated successfully", updatedAdmin);

    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<ResponseDTO> deleteAdmin(@RequestParam("id") Integer id) throws AdminNotFoundException {
        log.info("Received request to remove admins with ID: {}", id);
        if (id == null || id <= 0) {
            log.warn("Invalid ID provided: {}", id);
            return ResponseUtil.error("Invalid ID provided");
        }
        adminService.deleteAdminById(id);
        log.info("Successfully removed admin with ID: {}", id);
        return ResponseUtil.deleted("Admin deleted successfully", true);

    }
}
