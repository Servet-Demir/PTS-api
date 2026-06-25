package com.servet.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.servet.entities.MaasHesabi;
import com.servet.entities.MesaiKaydi;
import com.servet.entities.Personel;
import com.servet.repository.MaasHesabiRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MaasHesabiService {

    private final MaasHesabiRepository maasHesabiRepository;
    private final PersonelService personelService;
    private final MesaiKaydiService mesaiKaydiService;

    private static final BigDecimal yoneticiMaas = new BigDecimal("40000");
    private static final BigDecimal personelMaas = new BigDecimal("30000");
    private static final BigDecimal cezaTutari = new BigDecimal("500");

    public List<MaasHesabi> getAllMaasHesabiList() {
        List<MaasHesabi> maasHesabiList = maasHesabiRepository.findAll();
        log.info("{} salary calculation records listed.", maasHesabiList.size());
        return maasHesabiList;
    }

    public List<MaasHesabi> getMaasHesabiByDonem(LocalDate donem) {
        LocalDate ayBaslangic = donem.withDayOfMonth(1);

        List<MaasHesabi> maasHesabiList = maasHesabiRepository.findByDonem(ayBaslangic);

        log.info("{} salary calculation records listed for period: {}",
                maasHesabiList.size(),
                ayBaslangic);

        return maasHesabiList;
    }

    public MaasHesabi getMaasHesabiById(Long id) {
        return maasHesabiRepository.findById(id)
                .orElseGet(() -> {
                    log.warn("Salary calculation not found. ID: {}", id);
                    return null;
                });
    }

    public Optional<MaasHesabi> getMaasHesabiByPersonelAndDonem(Long personelId, LocalDate donem) {
        LocalDate ayBaslangic = donem.withDayOfMonth(1);

        Optional<MaasHesabi> maasHesabi = maasHesabiRepository.findFirstByPersonel_PersonelIdAndDonemOrderByMaasIdDesc(
                personelId,
                ayBaslangic);

        if (maasHesabi.isEmpty()) {
            log.info("No salary calculation found for employee ID: {}, period: {}", personelId, ayBaslangic);
        }

        return maasHesabi;
    }

    public MaasHesabi hesaplaMaas(Long personelId, LocalDate donem) {
        Personel personel = personelService.getPersonelById(personelId);

        if (personel == null) {
            log.warn("Salary calculation could not be completed. Employee not found. Employee ID: {}", personelId);
            throw new RuntimeException("Personel bulunamadı.");
        }

        LocalDate ayBaslangic = donem.withDayOfMonth(1);
        LocalDate ayBitis = ayBaslangic.plusMonths(1).minusDays(1);

        List<MesaiKaydi> mesaiListesi = mesaiKaydiService.getMesaiByPersonelAndTarihAraligi(
                personelId,
                ayBaslangic,
                ayBitis);

        if (mesaiListesi.isEmpty()) {
            log.warn("Salary calculation could not be completed. No attendance records. Employee ID: {}, Period: {}",
                    personelId,
                    ayBaslangic);

            throw new RuntimeException("Bu döneme ait mesai kaydı bulunmadığı için maaş hesaplanamaz.");
        }

        Optional<MaasHesabi> mevcutMaas = maasHesabiRepository
                .findFirstByPersonel_PersonelIdAndDonemOrderByMaasIdDesc(
                        personelId,
                        ayBaslangic);

        if (mevcutMaas.isPresent()) {
            log.info("Salary calculation already exists. Existing record returned. Employee ID: {}, Period: {}",
                    personelId,
                    ayBaslangic);

            return mevcutMaas.get();
        }

        boolean yoneticiMi = Boolean.TRUE.equals(personel.getYonetici());

        BigDecimal brutMaas = yoneticiMi ? yoneticiMaas : personelMaas;

        int gecersizGun = 0;

        if (!yoneticiMi) {
            for (MesaiKaydi kayit : mesaiListesi) {
                if (Boolean.FALSE.equals(kayit.getMesaiGecerli())) {
                    gecersizGun++;
                }
            }
        }

        BigDecimal ceza = cezaTutari.multiply(new BigDecimal(gecersizGun));
        BigDecimal netMaas = brutMaas.subtract(ceza);

        MaasHesabi maasHesabi = new MaasHesabi();
        maasHesabi.setPersonel(personel);
        maasHesabi.setDonem(ayBaslangic);
        maasHesabi.setBrutMaas(brutMaas);
        maasHesabi.setGecersizGun(gecersizGun);
        maasHesabi.setCeza(ceza);
        maasHesabi.setNetMaas(netMaas);

        MaasHesabi savedMaasHesabi = maasHesabiRepository.save(maasHesabi);

        log.info("Monthly salary calculated. ID: {}, Employee ID: {}, Period: {}, Invalid Days: {}, Net Salary: {}",
                savedMaasHesabi.getMaasId(),
                personelId,
                ayBaslangic,
                gecersizGun,
                netMaas);

        return savedMaasHesabi;
    }

    public boolean deleteMaasHesabi(Long id) {
        if (maasHesabiRepository.existsById(id)) {
            maasHesabiRepository.deleteById(id);
            log.info("Salary calculation deleted successfully. ID: {}", id);
            return true;
        }

        log.warn("Salary calculation could not be deleted. Record not found. ID: {}", id);
        return false;
    }
}