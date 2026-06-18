package com.servet.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servet.entities.MaasHesabi;
import com.servet.entities.MesaiKaydi;
import com.servet.entities.Personel;
import com.servet.repository.MaasHesabiRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MaasHesabiService {

    @Autowired
    private MaasHesabiRepository maasHesabiRepository;

    @Autowired
    private PersonelService personelService;

    @Autowired
    private MesaiKaydiService mesaiKaydiService;

    private static final BigDecimal yoneticiMaas = new BigDecimal("40000");
    private static final BigDecimal personelMaas = new BigDecimal("30000");
    private static final BigDecimal cezaTutari = new BigDecimal("500");

    public List<MaasHesabi> getAllMaasHesabiList() {
        log.info("Fetching all salary calculation records.");
        List<MaasHesabi> maasHesabiList = maasHesabiRepository.findAll();
        log.info("{} salary calculation records found.", maasHesabiList.size());
        return maasHesabiList;
    }

    public MaasHesabi getMaasHesabiById(Long id) {
        log.info("Fetching salary calculation by ID: {}", id);
        MaasHesabi maasHesabi = maasHesabiRepository.findById(id).orElse(null);

        if (maasHesabi == null) {
            log.warn("Salary calculation not found. ID: {}", id);
            return null;
        }
        log.info("Salary calculation found. ID: {}", id);
        return maasHesabi;
    }

    public MaasHesabi hesaplaMaas(Long personelId, LocalDate donem) {
        log.info("Salary calculation process started. Employee ID: {}, Period: {}", personelId, donem);
        Personel personel = personelService.getPersonelById(personelId);

        if (personel == null) {
            log.warn("Salary calculation could not be completed. Employee not found. Employee ID: {}", personelId);
            return null;
        }

        BigDecimal brutMaas = personel.getYonetici() ? yoneticiMaas : personelMaas;
        log.info("Gross salary determined. Employee ID: {}, Gross Salary: {}", personelId, brutMaas);
        List<MesaiKaydi> mesaiListesi = mesaiKaydiService.getMesaiByTarih(donem);
        log.info("{} attendance records found for period: {}", mesaiListesi.size(), donem);
        int gecersizGun = 0;

        if (!personel.getYonetici()) {
            log.info("Employee is not a manager. Invalid attendance days will be calculated. Employee ID: {}",
                    personelId);

            for (MesaiKaydi kayit : mesaiListesi) {
                if (kayit.getPersonel().getPersonelId().equals(personelId) // Long nesne tipinde karşılaştırma için
                                                                           // equals() kullanılır
                        && Boolean.FALSE.equals(kayit.getMesaiGecerli())) {
                    gecersizGun++;

                    log.info("Invalid attendance record found. Employee ID: {}, Attendance ID: {}",
                            personelId,
                            kayit.getMesaiId());
                }
            }
        } else {
            log.info("Employee is a manager. Attendance penalty will not be applied. Employee ID: {}", personelId);
        }

        BigDecimal ceza = cezaTutari.multiply(new BigDecimal(gecersizGun));
        BigDecimal netMaas = brutMaas.subtract(ceza);

        log.info("Salary penalty calculated. Employee ID: {}, Invalid Days: {}, Penalty: {}",
                personelId,
                gecersizGun,
                ceza);

        log.info("Net salary calculated. Employee ID: {}, Net Salary: {}", personelId, netMaas);

        MaasHesabi maasHesabi = new MaasHesabi();
        maasHesabi.setPersonel(personel);
        maasHesabi.setDonem(donem);
        maasHesabi.setBrutMaas(brutMaas);
        maasHesabi.setGecersizGun(gecersizGun);
        maasHesabi.setCeza(ceza);
        maasHesabi.setNetMaas(netMaas);
        MaasHesabi savedMaasHesabi = maasHesabiRepository.save(maasHesabi);

        log.info("Salary calculation saved successfully. Salary Calculation ID: {}, Employee ID: {}",
                savedMaasHesabi.getMaasId(),
                personelId);
        return savedMaasHesabi;
    }

    public boolean deleteMaasHesabi(Long id) {
        log.info("Salary calculation deletion process started. ID: {}", id);

        if (maasHesabiRepository.existsById(id)) {
            maasHesabiRepository.deleteById(id);
            log.info("Salary calculation deleted successfully. ID: {}", id);
            return true;
        }
        log.warn("Salary calculation could not be deleted. Record not found. ID: {}", id);
        return false;
    }
}