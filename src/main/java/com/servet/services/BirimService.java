package com.servet.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servet.entities.Birim;
import com.servet.repository.BirimRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j // Simple Logging Facade for Java
@Service
public class BirimService {
    @Autowired
    private BirimRepository birimRepository;

    public List<Birim> getAllBirimList() {
        List<Birim> birimler = birimRepository.findAll();
        log.info("{} department records listed.", birimler.size());
        return birimler;
    }

    public Birim getBirimById(Long id) {
        log.info("Fetching department by ID: {}", id);
        Birim birim = birimRepository.findById(id).orElse(null);
        log.info("Department found. ID: {}", id);
        return birim;
    }

    public Birim saveBirim(Birim birim) {
        log.info("Department creation process started.");
        Birim kaydedilenBirim = birimRepository.save(birim);
        log.info("Department created successfully. ID: {}", kaydedilenBirim.getBirimId());
        return kaydedilenBirim;
    }

    public boolean deleteBirim(Long id) {
        log.info("Department deletion process started. ID: {}", id);
        boolean varMi = birimRepository.existsById(id);

        if (varMi == false) {
            log.warn("Department could not be deleted. Department not found. ID: {}", id);
            return false;
        }
        birimRepository.deleteById(id);
        log.info("Department deleted successfully. ID: {}", id);

        return true;
    }

    public Birim updateBirim(Long id, Birim request) {
        log.info("Department update process started. ID: {}", id);
        Birim birim = birimRepository.findById(id).orElse(null);

        if (birim == null) {
            log.warn("Department could not be updated. Department not found. ID: {}", id);
            return null;
        }

        birim.setAd(request.getAd());

        Birim guncellenenBirim = birimRepository.save(birim);

        log.info("Department updated successfully. ID: {}", guncellenenBirim.getBirimId());
        return guncellenenBirim;
    }

}
