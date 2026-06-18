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
        List<MaasHesabi> maasHesabiList = maasHesabiRepository.findAll();
        log.info("{} salary calculation records listed.", maasHesabiList.size());
        return maasHesabiList;
    }

    public MaasHesabi getMaasHesabiById(Long id) {
        MaasHesabi maasHesabi = maasHesabiRepository.findById(id).orElse(null);

        if (maasHesabi == null) {
            log.warn("Salary calculation not found. ID: {}", id);
            return null;
        }

        return maasHesabi;
    }

    public MaasHesabi hesaplaMaas(Long personelId, LocalDate donem) {
        Personel personel = personelService.getPersonelById(personelId);

        if (personel == null) {
            log.warn("Salary calculation could not be completed. Employee not found. Employee ID: {}", personelId);
            return null;
        }

        BigDecimal brutMaas = personel.getYonetici() ? yoneticiMaas : personelMaas;
        List<MesaiKaydi> mesaiListesi = mesaiKaydiService.getMesaiByTarih(donem);

        int gecersizGun = 0;

        if (!personel.getYonetici()) {
            for (MesaiKaydi kayit : mesaiListesi) {
                if (kayit.getPersonel().getPersonelId().equals(personelId) // Long nesne tipinde karşılaştırma için
                                                                           // equals() kullanılır
                        && Boolean.FALSE.equals(kayit.getMesaiGecerli())) {
                    gecersizGun++;
                }
            }
        }

        BigDecimal ceza = cezaTutari.multiply(new BigDecimal(gecersizGun));
        BigDecimal netMaas = brutMaas.subtract(ceza);

        MaasHesabi maasHesabi = new MaasHesabi();
        maasHesabi.setPersonel(personel);
        maasHesabi.setDonem(donem);
        maasHesabi.setBrutMaas(brutMaas);
        maasHesabi.setGecersizGun(gecersizGun);
        maasHesabi.setCeza(ceza);
        maasHesabi.setNetMaas(netMaas);

        MaasHesabi savedMaasHesabi = maasHesabiRepository.save(maasHesabi);

        log.info("Salary calculation saved. ID: {}, Employee ID: {}, Period: {}, Invalid Days: {}, Net Salary: {}",
                savedMaasHesabi.getMaasId(),
                personelId,
                donem,
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