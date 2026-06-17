package com.servet.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servet.entities.Personel;
import com.servet.repository.PersonelRepository;
import com.servet.entities.Birim;

@Service
public class PersonelService {

    @Autowired
    private PersonelRepository personelRepository;

    @Autowired
    private BirimService birimService; // PersonelService içinde BirimService'e ihtiyaç var çünkü personel
                                       // kaydederkenveya güncellerken birim bilgisi de verilir ve bu bilgiyi
                                       // doğrulamak için BirimService'e ihtiyaç duyulur.

    public List<Personel> getAllPersonelList() {
        return personelRepository.findAll();
    }

    public Personel getPersonelById(Long id) {
        return personelRepository.findById(id).orElse(null);
    }

    public Personel savePersonel(Personel personel) {
        Birim birim = birimService.getBirimById(personel.getBirim().getBirimId());
        if (birim == null) {
            return null;
        }
        personel.setBirim(birim);
        return personelRepository.save(personel);
    }

    public boolean deletePersonel(Long id) {
        if (personelRepository.existsById(id)) {
            personelRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Personel updatePersonel(Long id, Personel request) {
        Personel personel = personelRepository.findById(id).orElse(null);
        if (personel != null) {
            personel.setAd(request.getAd());
            personel.setSoyad(request.getSoyad());
            personel.setEmail(request.getEmail());
            personel.setYonetici(request.getYonetici());
            if (request.getBirim() != null) {
                Birim birim = birimService.getBirimById(request.getBirim().getBirimId());
                if (birim != null) {
                    personel.setBirim(birim);
                }
            }
            return personelRepository.save(personel);
        }
        return null;
    }
}