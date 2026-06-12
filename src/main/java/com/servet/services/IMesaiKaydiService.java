package com.servet.services;

import java.time.LocalDate;
import java.util.List;

import com.servet.model.MesaiKaydi;

public interface IMesaiKaydiService {

    List<MesaiKaydi> getAllMesaiList();
    
    List<MesaiKaydi> getMesaiByTarih(LocalDate tarih);
    
    List<MesaiKaydi> getMesaiByBirimAndTarih(Long birimId, LocalDate tarih);
    
    List<MesaiKaydi> getMesaiByPersonelAndAy(Long personelId, LocalDate donem);
    
    MesaiKaydi saveMesai(MesaiKaydi mesaiKaydi);
    
    MesaiKaydi updateMesai(Long id, MesaiKaydi request);
    
    boolean deleteMesai(Long id);
}