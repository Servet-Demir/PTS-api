package com.servet.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servet.entities.Birim;
import com.servet.repository.BirimRepository;

@Service
public class BirimService {
    @Autowired
    private BirimRepository birimRepository;

    public List<Birim> getAllBirimList() {
        List<Birim> birimler = birimRepository.findAll();
        return birimler;
    }

    public Birim getBirimById(Long id) {
        Birim birim = birimRepository.findById(id).orElse(null);
        return birim;
    }

    public Birim saveBirim(Birim birim) {
        Birim kaydedilenBirim = birimRepository.save(birim);
        return kaydedilenBirim;
    }

    public boolean deleteBirim(Long id) {
        boolean varMi = birimRepository.existsById(id);

        if (varMi == false) {
            return false;
        }

        birimRepository.deleteById(id);
        return true;
    }

    public Birim updateBirim(Long id, Birim request) {
        Birim birim = birimRepository.findById(id).orElse(null);

        if (birim == null) {
            return null;
        }

        birim.setAd(request.getAd());

        return birimRepository.save(birim);
    }

}
