package com.servet.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "mesai_kayitlari", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"personelId", "tarih"})
})
public class MesaiKaydi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mesaiId;

    @ManyToOne
    @JoinColumn(name = "personel_id", nullable = false)
    private Personel personel;

    @Column(nullable = false)
    private LocalDate tarih;

    private LocalTime girisSaati;

    private LocalTime cikisSaati;

    private Boolean mesaiGecerli;

    public Long getMesaiId() { return mesaiId; }
    public void setMesaiId(Long mesaiId) { this.mesaiId = mesaiId; }

    public Personel getPersonel() { return personel; }
    public void setPersonel(Personel personel) { this.personel = personel; }

    public LocalDate getTarih() { return tarih; }
    public void setTarih(LocalDate tarih) { this.tarih = tarih; }

    public LocalTime getGirisSaati() { return girisSaati; }
    public void setGirisSaati(LocalTime girisSaati) { this.girisSaati = girisSaati; }

    public LocalTime getCikisSaati() { return cikisSaati; }
    public void setCikisSaati(LocalTime cikisSaati) { this.cikisSaati = cikisSaati; }

    public Boolean getMesaiGecerli() { return mesaiGecerli; }
    public void setMesaiGecerli(Boolean mesaiGecerli) { this.mesaiGecerli = mesaiGecerli; }
}