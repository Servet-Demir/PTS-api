package com.servet.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.servet.entities.Birim;
import com.servet.services.BirimService;

@RestController
@RequestMapping("/rest/api/birim")
public class BirimController {

    private final BirimService birimService;

    public BirimController(BirimService birimService) {
        this.birimService = birimService;
    }

    @GetMapping("/list")
    public List<Birim> getAllBirimList() {
        return birimService.getAllBirimList();
    }

    @GetMapping("/list/{id}")
    public Birim getBirimById(@PathVariable Long id) {
        return birimService.getBirimById(id);
    }

    @PostMapping("/save")
    public Birim saveBirim(@RequestBody Birim birim) {
        return birimService.saveBirim(birim);
    }

    @PutMapping("/update/{id}")
    public Birim updateBirim(@PathVariable Long id, @RequestBody Birim birim) {
        return birimService.updateBirim(id, birim);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteBirim(@PathVariable Long id) {
        return birimService.deleteBirim(id);
    }
}
