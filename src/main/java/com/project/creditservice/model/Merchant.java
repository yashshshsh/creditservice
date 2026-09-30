package com.project.creditservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "merchants")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String businessName;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    private String businessType;

    private String city;

    private Boolean active = true;
}