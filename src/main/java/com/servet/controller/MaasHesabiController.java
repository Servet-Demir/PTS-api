package com.servet.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.servet.entities.MaasHesabi;
import com.servet.services.MaasHesabiService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/api/maas")
public class MaasHesabiController {

    private final MaasHesabiService maasHesabiService;

    @GetMapping("/list")
    public List<MaasHesabi> getAllMaasHesabiList() {
        return maasHesabiService.getAllMaasHesabiList();
    }

    @GetMapping("/list/{id}")
    public MaasHesabi getMaasHesabiById(@PathVariable Long id) {
        return maasHesabiService.getMaasHesabiById(id);
    }

    @PostMapping("/hesapla/{personelId}")
    public ResponseEntity<?> hesaplaMaas(
            @PathVariable Long personelId,
            @RequestParam LocalDate donem) {
        try {
            MaasHesabi maasHesabi = maasHesabiService.hesaplaMaas(personelId, donem);
            return ResponseEntity.ok(maasHesabi);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/donem")
    public List<MaasHesabi> getMaasHesabiByDonem(
            @RequestParam("donem") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate donem) {
        return maasHesabiService.getMaasHesabiByDonem(donem);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteMaasHesabi(@PathVariable Long id) {
        return maasHesabiService.deleteMaasHesabi(id);
    }
}