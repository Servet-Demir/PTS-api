package com.servet.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "birimler")
@Data

public class Birim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long birimId;

    @Column(nullable = false, unique = true)
    private String ad;

}