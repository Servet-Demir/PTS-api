package com.servet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.servet.model.Birim;

@Repository
public interface BirimRepository extends JpaRepository<Birim, Long> {

}