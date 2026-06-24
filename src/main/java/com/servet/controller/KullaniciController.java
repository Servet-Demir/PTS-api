package com.servet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.servet.entities.Kullanici;
import com.servet.services.KullaniciService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rest/api/kullanici")
@RequiredArgsConstructor
public class KullaniciController {

    private final KullaniciService kullaniciService;

    @PostMapping("/login")
    public ResponseEntity<Kullanici> login(@RequestBody Kullanici request) {

        Kullanici kullanici = kullaniciService.login(request);

        if (kullanici == null) {
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.ok(kullanici);
    }
}