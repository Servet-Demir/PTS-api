package com.servet.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "personeller")
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

    @ManyToOne 
    @JoinColumn(name = "birim_id" , nullable = false)
    private Birim birim;

    public Long getPersonelId() { return personelId; }
    public void setPersonelId(Long personelId) { this.personelId = personelId; }

    public String getAd() { return ad; }
    public void setAd(String ad) { this.ad = ad; }

    public String getSoyad() { return soyad; }
    public void setSoyad(String soyad) { this.soyad = soyad; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Boolean getYonetici() { return yonetici; }
    public void setYonetici(Boolean yonetici) { this.yonetici = yonetici; }

    public Birim getBirim() { return birim; }
    public void setBirim(Birim birim) { this.birim = birim; }
}