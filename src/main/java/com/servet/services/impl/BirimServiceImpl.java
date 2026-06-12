package com.servet.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servet.model.Birim;
import com.servet.repository.BirimRepository;
import com.servet.services.IBirimService;

@Service
public class BirimServiceImpl implements IBirimService {

    @Autowired
    private BirimRepository birimRepository;

    @Override
    public List<Birim> getAllBirimList() {
        return birimRepository.findAll();
    }

    @Override
    public Birim getBirimById(Long id) {
        return birimRepository.findById(id).orElse(null);
    }

    @Override
    public Birim saveBirim(Birim birim) {
        return birimRepository.save(birim);
    }

    @Override
    public boolean deleteBirim(Long id) {
        if (birimRepository.existsById(id)) {
            birimRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Birim updateBirim(Long id, Birim request) {
        Birim birim = birimRepository.findById(id).orElse(null);
        if (birim != null) {
            birim.setAd(request.getAd());
            return birimRepository.save(birim);
        }
        return null;
    }
}