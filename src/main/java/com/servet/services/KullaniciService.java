package com.servet.services;

import org.springframework.stereotype.Service;

import com.servet.entities.Kullanici;
import com.servet.repository.KullaniciRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class KullaniciService {

    private final KullaniciRepository kullaniciRepository;

    public Kullanici login(Kullanici request) {

        if (request == null) {
            log.warn("Login failed. Request body is null.");
            return null;
        }

        if (request.getEmail() == null || request.getSifre() == null) {
            log.warn("Login failed. Email or password is null.");
            return null;
        }

        Kullanici kullanici = kullaniciRepository.findByEmail(request.getEmail())
                .orElse(null);

        if (kullanici == null) {
            log.warn("Login failed. User not found. Email: {}", request.getEmail());
            return null;
        }

        if (!kullanici.getSifre().equals(request.getSifre())) {
            log.warn("Login failed. Wrong password. Email: {}", request.getEmail());
            return null;
        }

        log.info("Login successful. User ID: {}", kullanici.getKullaniciId());

        return kullanici;
    }
}