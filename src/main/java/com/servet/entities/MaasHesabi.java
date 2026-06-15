package com.servet.entities;

import java.math.BigInteger;
import java.time.LocalDate;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "maas_hesaplari")

public class MaasHesabi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public BigInteger maasId;

    @Column(nullable = false)
    public BigDecimal brutMaas;

    @Column(nullable = false)
    public BigDecimal ceza;

    @Column(nullable = false)
    public LocalDate donem;

    @Column(nullable = false)
    public Integer gecersizGun;

    @Column(nullable = false)
    public BigDecimal netMaas;

    @ManyToOne
    @JoinColumn(name = "personel_id", nullable = false)
    private Personel personel;

}
