package com.servet.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.servet.dto.PersonelDonemOzeti;
import com.servet.entities.Personel;
import com.servet.services.PersonelService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/api/personel")
public class PersonelController {

    private final PersonelService personelService;

    @GetMapping("/list")
    public List<Personel> getAllPersonelList() {
        return personelService.getAllPersonelList();
    }

    @GetMapping("/list/{id}")
    public Personel getPersonelById(@PathVariable Long id) {
        return personelService.getPersonelById(id);
    }

    @GetMapping("/{id}/mesai")
    public ResponseEntity<PersonelDonemOzeti> getPersonelDonemOzeti(
            @PathVariable Long id,
            @RequestParam("donem") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate donem) {
        return personelService.getPersonelDonemOzeti(id, donem)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public Personel savePersonel(@RequestBody Personel personel) {
        return personelService.savePersonel(personel);
    }

    @PutMapping("/update/{id}")
    public Personel updatePersonel(@PathVariable Long id, @RequestBody Personel personel) {
        return personelService.updatePersonel(id, personel);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deletePersonel(@PathVariable Long id) {
        return personelService.deletePersonel(id);
    }
}