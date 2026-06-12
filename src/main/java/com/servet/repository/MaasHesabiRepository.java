package com.servet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.servet.model.MaasHesabi;

@Repository
public interface MaasHesabiRepository extends JpaRepository<MaasHesabi, Long> {

}