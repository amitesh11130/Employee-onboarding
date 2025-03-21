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
@Table(name = "employee_onboarding")
public class Employee {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String contact;
    private String email;
    private String dateOfBirth;

}
