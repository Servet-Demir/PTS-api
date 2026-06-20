package com.servet.controller;

import java.util.List;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.servet.entities.MesaiKaydi;
import com.servet.services.MesaiKaydiService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/api/mesai")
public class MesaiKaydiController {

    private final MesaiKaydiService mesaiKaydiService;

    @GetMapping("/list")
    public List<MesaiKaydi> getAllMesaiList() {
        return mesaiKaydiService.getAllMesaiList();
    }

    @GetMapping("/list/{tarih}")
    public List<MesaiKaydi> getMesaiByTarih(@PathVariable LocalDate tarih) {
        return mesaiKaydiService.getMesaiByTarih(tarih);
    }

    @GetMapping("/personel/{personelId}")
    public List<MesaiKaydi> getMesaiByPersonelAndDonem(
            @PathVariable Long personelId,
            @RequestParam("donem") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate donem) {
        return mesaiKaydiService.getMesaiByPersonelAndDonem(personelId, donem);
    }

    @PostMapping("/save")
    public MesaiKaydi saveMesai(@RequestBody MesaiKaydi mesaiKaydi) {
        return mesaiKaydiService.saveMesai(mesaiKaydi);
    }

    @PutMapping("/update/{id}")
    public MesaiKaydi updateMesai(@PathVariable Long id, @RequestBody MesaiKaydi mesaiKaydi) {
        return mesaiKaydiService.updateMesai(id, mesaiKaydi);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteMesai(@PathVariable Long id) {
        return mesaiKaydiService.deleteMesai(id);
    }
}