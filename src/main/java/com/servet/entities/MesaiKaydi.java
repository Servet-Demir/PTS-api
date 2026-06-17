package com.servet.entities;

import java.time.LocalTime;
import java.time.LocalDate;

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
@Table(name = "mesai_kayitlari")
public class MesaiKaydi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mesaiId;

    @Column(name = "cikis_saati")
    private LocalTime cikisSaati;

    @Column(name = "giris_saati")
    private LocalTime girisSaati;

    @Column(name = "mesai_gecerli")
    private boolean mesaiGecerli;

    @Column(name = "tarih")
    private LocalDate tarih;

    @ManyToOne
    @JoinColumn(name = "personel_id", nullable = false)
    private Personel personel;
}
