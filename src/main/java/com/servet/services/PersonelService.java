package com.servet.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servet.entities.Personel;
import com.servet.repository.PersonelRepository;
import com.servet.entities.Birim;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PersonelService {

    @Autowired
    private PersonelRepository personelRepository;

    @Autowired
    private BirimService birimService;

    public List<Personel> getAllPersonelList() {
        log.info("Fetching all employee records.");

        List<Personel> personelList = personelRepository.findAll();

        log.info("{} employee records found.", personelList.size());

        return personelList;
    }

    public Personel getPersonelById(Long id) {
        log.info("Fetching employee by ID: {}", id);

        Personel personel = personelRepository.findById(id).orElse(null);

        if (personel == null) {
            log.warn("Employee not found. ID: {}", id);
            return null;
        }

        log.info("Employee found. ID: {}", id);

        return personel;
    }

    public Personel savePersonel(Personel personel) {
        log.info("Employee creation process started.");

        if (personel == null) {
            log.warn("Employee could not be created. Request body is null.");
            return null;
        }

        if (personel.getBirim() == null || personel.getBirim().getBirimId() == null) {
            log.warn("Employee could not be created. Department information is required.");
            return null;
        }

        Long birimId = personel.getBirim().getBirimId();

        Birim birim = birimService.getBirimById(birimId);

        if (birim == null) {
            log.warn("Employee could not be created. Department not found. Department ID: {}", birimId);
            return null;
        }

        personel.setBirim(birim);

        Personel savedPersonel = personelRepository.save(personel);

        log.info("Employee created successfully. ID: {}", savedPersonel.getPersonelId());

        return savedPersonel;
    }

    public boolean deletePersonel(Long id) {
        log.info("Employee deletion process started. ID: {}", id);

        if (personelRepository.existsById(id)) {
            personelRepository.deleteById(id);

            log.info("Employee deleted successfully. ID: {}", id);

            return true;
        }

        log.warn("Employee could not be deleted. Employee not found. ID: {}", id);

        return false;
    }

    public Personel updatePersonel(Long id, Personel request) {
        log.info("Employee update process started. ID: {}", id);

        Personel personel = personelRepository.findById(id).orElse(null);

        if (personel == null) {
            log.warn("Employee could not be updated. Employee not found. ID: {}", id);
            return null;
        }

        if (request == null) {
            log.warn("Employee could not be updated. Request body is null. ID: {}", id);
            return null;
        }

        personel.setAd(request.getAd());
        personel.setSoyad(request.getSoyad());
        personel.setEmail(request.getEmail());
        personel.setYonetici(request.getYonetici());

        log.info("Employee basic information updated. ID: {}", id);

        if (request.getBirim() != null && request.getBirim().getBirimId() != null) {
            Long birimId = request.getBirim().getBirimId();

            Birim birim = birimService.getBirimById(birimId);

            if (birim != null) {
                personel.setBirim(birim);

                log.info("Employee department updated. Employee ID: {}, Department ID: {}", id, birimId);
            } else {
                log.warn("Employee department could not be updated. Department not found. Department ID: {}", birimId);
            }
        }

        Personel updatedPersonel = personelRepository.save(personel);

        log.info("Employee updated successfully. ID: {}", updatedPersonel.getPersonelId());

        return updatedPersonel;
    }
}