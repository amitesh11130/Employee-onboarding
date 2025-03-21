package com.springbootapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Admin {

    @Id
    @GeneratedValue
    private int id;
    @Column(unique = true)
    private String adminName;
    private String password;
    private boolean isActive;
    private String role;

}
