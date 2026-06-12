package com.servet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.servet.model.Personel;
import com.servet.services.IPersonelService;

@RestController
@RequestMapping("/rest/api/personel")
public class PersonelController {

    @Autowired
    private IPersonelService personelService;

    @GetMapping("/list")
    public List<Personel> getAllPersonelList() {
        return personelService.getAllPersonelList();
    }

    @GetMapping("/list/{id}")
    public Personel getPersonelById(@PathVariable Long id) {
        return personelService.getPersonelById(id);
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