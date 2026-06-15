package com.servet.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "personeller")
@Data

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

    // başka tabloya bağlı sutunlar ilişki türü ve @JoinColumn ile tanımlanır
    @ManyToOne
    @JoinColumn(name = "birim_id", nullable = false)
    private Birim birim;

}
