package com.servet.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.servet.entities.MaasHesabi;

public interface MaasHesabiRepository extends JpaRepository<MaasHesabi, Long> {

    List<MaasHesabi> findByDonem(LocalDate donem);

    List<MaasHesabi> findByPersonel_PersonelId(Long personelId);

    Optional<MaasHesabi> findFirstByPersonel_PersonelIdAndDonemOrderByMaasIdDesc(
            Long personelId,
            LocalDate donem);
}