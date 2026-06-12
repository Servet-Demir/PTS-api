package com.servet.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.servet.model.MesaiKaydi;

@Repository
public interface MesaiKaydiRepository extends JpaRepository<MesaiKaydi, Long> {

    List<MesaiKaydi> findByTarih(LocalDate tarih);

    List<MesaiKaydi> findByPersonel_BirimBirimIdAndTarih(Long birimId, LocalDate tarih);

    List<MesaiKaydi> findByPersonel_PersonelIdAndTarihBetween(Long personelId, LocalDate baslangic, LocalDate bitis);
}