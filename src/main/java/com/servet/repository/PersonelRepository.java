package com.servet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.servet.entities.Personel;

// @Repository // yazmaya gerek yok Spring Data JPA bu interfacein bir repo olduğunu anlıtor
public interface PersonelRepository extends JpaRepository<Personel, Long> {

}
