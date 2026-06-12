package com.servet.model;

import java.math.BigDecimal;
import java.time.LocalDate;

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
@Table(name = "maas_hesaplari", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"personelId", "donem"})
})
public class MaasHesabi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long maasId;

    @ManyToOne
    @JoinColumn(name = "personel_id", nullable = false)
    private Personel personel;

    @Column(nullable = false)
    private LocalDate donem;

    @Column(nullable = false)
    private BigDecimal brutMaas;

    @Column(nullable = false)
    private Integer gecersizGun = 0;

    @Column(nullable = false)
    private BigDecimal ceza = BigDecimal.ZERO;

    @Column(nullable = false)
    private BigDecimal netMaas;

    public Long getMaasId() { return maasId; }
    public void setMaasId(Long maasId) { this.maasId = maasId; }

    public Personel getPersonel() { return personel; }
    public void setPersonel(Personel personel) { this.personel = personel; }

    public LocalDate getDonem() { return donem; }
    public void setDonem(LocalDate donem) { this.donem = donem; }

    public BigDecimal getBrutMaas() { return brutMaas; }
    public void setBrutMaas(BigDecimal brutMaas) { this.brutMaas = brutMaas; }

    public Integer getGecersizGun() { return gecersizGun; }
    public void setGecersizGun(Integer gecersizGun) { this.gecersizGun = gecersizGun; }

    public BigDecimal getCeza() { return ceza; }
    public void setCeza(BigDecimal ceza) { this.ceza = ceza; }

    public BigDecimal getNetMaas() { return netMaas; }
    public void setNetMaas(BigDecimal netMaas) { this.netMaas = netMaas; }
}