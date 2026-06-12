package com.servet.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servet.model.Birim;
import com.servet.model.Personel;
import com.servet.repository.PersonelRepository;
import com.servet.services.IBirimService;
import com.servet.services.IPersonelService;

@Service
public class PersonelServiceImpl implements IPersonelService {

    @Autowired
    private PersonelRepository personelRepository;

    @Autowired
    private IBirimService birimService;

    @Override
    public List<Personel> getAllPersonelList() {
        return personelRepository.findAll();
    }

    @Override
    public Personel getPersonelById(Long id) {
        return personelRepository.findById(id).orElse(null);
    }

    @Override
    public Personel savePersonel(Personel personel) {
        Birim birim = birimService.getBirimById(personel.getBirim().getBirimId());
        if (birim == null) {
            return null;
        }
        personel.setBirim(birim);
        return personelRepository.save(personel);
    }

    @Override
    public boolean deletePersonel(Long id) {
        if (personelRepository.existsById(id)) {
            personelRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
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