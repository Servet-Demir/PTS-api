package com.servet.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.servet.entities.MesaiKaydi;;

public interface MesaiKaydiRepository extends JpaRepository<MesaiKaydi, Long> {

    List<MesaiKaydi> findByTarih(LocalDate tarih);

    List<MesaiKaydi> findByTarihBetween(LocalDate baslangicTarihi, LocalDate bitisTarihi);

    List<MesaiKaydi> findByPersonel_PersonelId(Long personelId);

    List<MesaiKaydi> findByPersonel_PersonelIdAndTarihBetween(
            Long personelId,
            LocalDate baslangicTarihi,
            LocalDate bitisTarihi);

}