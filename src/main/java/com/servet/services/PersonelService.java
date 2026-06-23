package com.servet.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.servet.dto.PersonelDonemOzeti;
import com.servet.entities.MaasHesabi;
import com.servet.entities.MesaiKaydi;
import com.servet.entities.Personel;
import com.servet.repository.MaasHesabiRepository;
import com.servet.repository.MesaiKaydiRepository;
import com.servet.repository.PersonelRepository;
import com.servet.entities.Birim;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class PersonelService {

    private final PersonelRepository personelRepository;
    private final MesaiKaydiRepository mesaiKaydiRepository;
    private final MaasHesabiRepository maasHesabiRepository;
    private final BirimService birimService;

    public List<Personel> getAllPersonelList() {
        List<Personel> personelList = personelRepository.findAll();
        log.info("{} employee records listed.", personelList.size());
        return personelList;
    }

    public Personel getPersonelById(Long id) {
        Personel personel = personelRepository.findById(id).orElse(null);

        if (personel == null) {
            log.warn("Employee not found. ID: {}", id);
            return null;
        }

        return personel;
    }

    public Optional<PersonelDonemOzeti> getPersonelDonemOzeti(Long personelId, LocalDate donem) {
        return personelRepository.findById(personelId)
                .map(personel -> {
                    LocalDate ayBaslangic = donem.withDayOfMonth(1);
                    LocalDate ayBitis = donem.withDayOfMonth(donem.lengthOfMonth());

                    List<MesaiKaydi> mesaiKayitlari = mesaiKaydiRepository.findByPersonel_PersonelIdAndTarihBetween(
                            personelId,
                            ayBaslangic,
                            ayBitis);

                    Optional<MaasHesabi> maasHesabi = maasHesabiRepository
                            .findByPersonel_PersonelIdAndDonem(personelId, ayBaslangic);

                    log.info(
                            "Period summary listed for employee ID: {}, period: {}-{}, attendance count: {}, salary present: {}",
                            personelId,
                            ayBaslangic,
                            ayBitis,
                            mesaiKayitlari.size(),
                            maasHesabi.isPresent());

                    return new PersonelDonemOzeti(personel, mesaiKayitlari, maasHesabi);
                });
    }

    public Personel savePersonel(Personel personel) {
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
        if (personelRepository.existsById(id)) {
            personelRepository.deleteById(id);

            log.info("Employee deleted successfully. ID: {}", id);

            return true;
        }

        log.warn("Employee could not be deleted. Employee not found. ID: {}", id);

        return false;
    }

    public Personel updatePersonel(Long id, Personel request) {
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

        if (request.getBirim() != null && request.getBirim().getBirimId() != null) {
            Long birimId = request.getBirim().getBirimId();

            Birim birim = birimService.getBirimById(birimId);

            if (birim != null) {
                personel.setBirim(birim);
            } else {
                log.warn("Employee department could not be updated. Department not found. Department ID: {}", birimId);
            }
        }

        Personel updatedPersonel = personelRepository.save(personel);

        log.info("Employee updated successfully. ID: {}", updatedPersonel.getPersonelId());

        return updatedPersonel;
    }
}