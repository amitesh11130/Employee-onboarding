package com.springbootapi.services;

import com.springbootapi.entity.Admin;
import com.springbootapi.repository.AdminRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AdminServicesSecurity implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String adminName) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByAdminName(adminName);
        log.info("Received request to check valid admin or not with adminName: {}", adminName);
        if (admin == null) {
            log.warn("Admin not found with given adminName: {}", adminName);
            throw new UsernameNotFoundException("Admin not found with given adminName : " + adminName);
        }

        log.info("Successfully authenticated admin with adminName: {}", adminName);

        return User.builder()
                .username(admin.getAdminName())
                .password(admin.getPassword())
                .roles(admin.getRole())
                .disabled(!admin.isActive())
                .build();
    }


}
