package com.servet.services;

import java.time.LocalDate;
import java.util.List;

import com.servet.model.MaasHesabi;

public interface IMaasHesabiService {

    List<MaasHesabi> getAllMaasHesabiList();

    MaasHesabi getMaasHesabiById(Long id);

    MaasHesabi hesaplaMaas(Long personelId, LocalDate donem);

    boolean deleteMaasHesabi(Long id);
}