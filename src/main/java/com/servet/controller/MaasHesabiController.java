package com.servet.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.servet.model.MaasHesabi;
import com.servet.services.IMaasHesabiService;

@RestController
@RequestMapping("/rest/api/maas")
public class MaasHesabiController {

    @Autowired
    private IMaasHesabiService maasHesabiService;

    @GetMapping("/list")
    public List<MaasHesabi> getAllMaasHesabiList() {
        return maasHesabiService.getAllMaasHesabiList();
    }

    @GetMapping("/list/{id}")
    public MaasHesabi getMaasHesabiById(@PathVariable Long id) {
        return maasHesabiService.getMaasHesabiById(id);
    }

    @PostMapping("/hesapla")
    public MaasHesabi hesaplaMaas(@RequestParam Long personelId,
                                   @RequestParam LocalDate donem) {
        return maasHesabiService.hesaplaMaas(personelId, donem);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteMaasHesabi(@PathVariable Long id) {
        return maasHesabiService.deleteMaasHesabi(id);
    }
}