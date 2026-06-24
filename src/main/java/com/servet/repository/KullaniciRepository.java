package com.servet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.servet.entities.Kullanici;

public interface KullaniciRepository extends JpaRepository<Kullanici, Long> {

    Optional<Kullanici> findByEmail(String email); // bu maile sahip kullanıcı bulunursa onu döndür, yoksa boş döndür
}