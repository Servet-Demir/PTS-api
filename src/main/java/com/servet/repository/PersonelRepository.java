package com.servet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.servet.model.Personel;

@Repository
public interface PersonelRepository extends JpaRepository<Personel, Long> {

}