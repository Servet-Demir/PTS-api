package com.servet.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.servet.entities.MesaiKaydi;;

public interface MesaiKaydiRepository extends JpaRepository<MesaiKaydi, Long> {

    List<MesaiKaydi> findByTarih(Date tarih);

    List<MesaiKaydi> findByPersonel_PersonelId(Long personelId);

}