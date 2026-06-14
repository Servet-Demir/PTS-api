package com.servet.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Personeller")

public class Personel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personelId;

    @Column(nullable = false)
    private String ad;

    @Column(nullable = false)
    private String soyad;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private Boolean yonetici = false;
}
