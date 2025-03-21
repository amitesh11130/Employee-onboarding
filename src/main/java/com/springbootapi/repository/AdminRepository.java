package com.springbootapi.repository;

import com.springbootapi.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface AdminRepository extends JpaRepository<Admin,Integer> {

    Admin findByAdminName(String adminName);
}
