package com.servet.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.servet.model.MesaiKaydi;
import com.servet.services.IMesaiKaydiService;

@RestController
@RequestMapping("/rest/api/mesai")
public class MesaiKaydiController {

    @Autowired
    private IMesaiKaydiService mesaiKaydiService;

    @GetMapping("/list")
    public List<MesaiKaydi> getAllMesaiList() {
        return mesaiKaydiService.getAllMesaiList();
    }

    @GetMapping("/list/tarih")
    public List<MesaiKaydi> getMesaiByTarih(@RequestParam LocalDate tarih) {
        return mesaiKaydiService.getMesaiByTarih(tarih);
    }

    @GetMapping("/list/birim")
    public List<MesaiKaydi> getMesaiByBirimAndTarih(@RequestParam Long birimId,
                                                     @RequestParam LocalDate tarih) {
        return mesaiKaydiService.getMesaiByBirimAndTarih(birimId, tarih);
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