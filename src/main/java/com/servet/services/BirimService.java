package com.servet.services;

import java.util.List;
import org.springframework.stereotype.Service;

import com.servet.entities.Birim;
import com.servet.repository.BirimRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j // Simple Logging Facade for Java
@RequiredArgsConstructor
@Service
public class BirimService {
    private final BirimRepository birimRepository;

    public List<Birim> getAllBirimList() {
        List<Birim> birimler = birimRepository.findAll();
        log.info("{} department records listed.", birimler.size());
        return birimler;
    }

    public Birim getBirimById(Long id) {
        Birim birim = birimRepository.findById(id).orElse(null);

        if (birim == null) {
            log.warn("Department not found. ID: {}", id);
        }

        return birim;
    }

    public Birim saveBirim(Birim birim) {
        Birim kaydedilenBirim = birimRepository.save(birim);
        log.info("Department created successfully. ID: {}", kaydedilenBirim.getBirimId());
        return kaydedilenBirim;
    }

    public boolean deleteBirim(Long id) {
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