package com.servet.entities;

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
@NoArgsConstructor // parametresiz constructor oluşturur. Personel personel = new Personel();
// public Personel() {
// }
@AllArgsConstructor // tüm fieldlari parametre olarak alan constructor oluşturur.
// public Personel(Long personelId, String ad, String soyad, String telefon) {
// this.personelId = personelId;
// this.ad = ad;
// this.soyad = soyad;
// this.telefon = telefon;
// }

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

    // başka tabloya bağlı sutunlar ilişki türü ve @JoinColumn ile tanımlanır
    @ManyToOne
    @JoinColumn(name = "birim_id", nullable = false)
    private Birim birim;

}
