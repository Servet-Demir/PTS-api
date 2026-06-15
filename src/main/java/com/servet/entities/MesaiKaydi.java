package com.servet.entities;

import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;

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
@Table(name = "mesai_kayitlari")
@Data
public class MesaiKaydi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public BigInteger mesai_id;

    @Column
    public Time cikis_saati;

    @Column
    public Time giris_saati;

    @Column
    public boolean mesaiGecerli;

    @Column
    public Date tarih;

    @ManyToOne
    @JoinColumn(name = "personel_id", nullable = false)
    private Personel personel;

}
