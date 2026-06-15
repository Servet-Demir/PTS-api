package com.servet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.servet.entities.Birim;;

// @Repository // yazmaya gerek yok Spring Data JPA bu interfacein bir repo olduğunu anlıtor
public interface BirimRepository extends JpaRepository<Birim, Long> {

}
