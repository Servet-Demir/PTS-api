package com.servet.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaasDonemOzetiResponse {

    private Long personelId;

    private String ad;

    private String soyad;

    private Boolean yonetici;

    private LocalDate donem;

    private Integer toplamMesaiKaydi;

    private Integer gecersizGun;

    private BigDecimal brutMaas;

    private BigDecimal ceza;

    private BigDecimal netMaas;
}