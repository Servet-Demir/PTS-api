package com.servet.controller;

import java.util.List;
import java.time.LocalDate;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.servet.entities.MesaiKaydi;
import com.servet.services.MesaiKaydiService;

@RestController
@RequestMapping("/rest/api/mesai")
public class MesaiKaydiController {

    private final MesaiKaydiService mesaiKaydiService;

    public MesaiKaydiController(MesaiKaydiService mesaiKaydiService) {
        this.mesaiKaydiService = mesaiKaydiService;
    }

    @GetMapping("/list")
    public List<MesaiKaydi> getAllMesaiList() {
        return mesaiKaydiService.getAllMesaiList();
    }

    @GetMapping("/list/{tarih}")
    public List<MesaiKaydi> getMesaiByTarih(@PathVariable LocalDate tarih) {
        return mesaiKaydiService.getMesaiByTarih(tarih);
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