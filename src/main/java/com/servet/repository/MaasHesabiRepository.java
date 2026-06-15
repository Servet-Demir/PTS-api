package com.servet.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.servet.entities.MaasHesabi;

public interface MaasHesabiRepository extends JpaRepository<MaasHesabi, Long> {

    List<MaasHesabi> findByDonem(LocalDate donem);

    List<MaasHesabi> findByPersonel_PersonelId(Long personelId);
}