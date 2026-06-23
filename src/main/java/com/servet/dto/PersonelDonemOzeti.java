package com.servet.dto;

import java.util.List;
import java.util.Optional;

import com.servet.entities.MaasHesabi;
import com.servet.entities.MesaiKaydi;
import com.servet.entities.Personel;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PersonelDonemOzeti {

    private Personel personel;
    private List<MesaiKaydi> mesaiKayitlari;
    private Optional<MaasHesabi> maasHesabi;
}
