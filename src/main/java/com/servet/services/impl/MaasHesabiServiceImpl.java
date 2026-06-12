package com.servet.services.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servet.model.MaasHesabi;
import com.servet.model.MesaiKaydi;
import com.servet.model.Personel;
import com.servet.repository.MaasHesabiRepository;
import com.servet.services.IMaasHesabiService;
import com.servet.services.IMesaiKaydiService;
import com.servet.services.IPersonelService;

@Service
public class MaasHesabiServiceImpl implements IMaasHesabiService {

    @Autowired
    private MaasHesabiRepository maasHesabiRepository;

    @Autowired
    private IPersonelService personelService;

    @Autowired
    private IMesaiKaydiService mesaiKaydiService;

    private static final BigDecimal YONETICI_MAAS = new BigDecimal("40000");
    private static final BigDecimal PERSONEL_MAAS = new BigDecimal("30000");
    private static final BigDecimal CEZA_TUTARI = new BigDecimal("500");

    @Override
    public List<MaasHesabi> getAllMaasHesabiList() {
        return maasHesabiRepository.findAll();
    }

    @Override
    public MaasHesabi getMaasHesabiById(Long id) {
        return maasHesabiRepository.findById(id).orElse(null);
    }

    @Override
    public MaasHesabi hesaplaMaas(Long personelId, LocalDate donem) {
        Personel personel = personelService.getPersonelById(personelId);
        if (personel == null) {
            return null;
        }

        BigDecimal brutMaas = personel.getYonetici() ? YONETICI_MAAS : PERSONEL_MAAS;

        List<MesaiKaydi> mesaiListesi = mesaiKaydiService.getMesaiByPersonelAndAy(personelId, donem);

        int gecersizGun = 0;
        if (!personel.getYonetici()) {
            for (MesaiKaydi kaydi : mesaiListesi) {
                if (Boolean.FALSE.equals(kaydi.getMesaiGecerli())) {
                    gecersizGun++;
                }
            }
        }

        BigDecimal ceza = CEZA_TUTARI.multiply(new BigDecimal(gecersizGun));
        BigDecimal netMaas = brutMaas.subtract(ceza);

        MaasHesabi maasHesabi = new MaasHesabi();
        maasHesabi.setPersonel(personel);
        maasHesabi.setDonem(donem);
        maasHesabi.setBrutMaas(brutMaas);
        maasHesabi.setGecersizGun(gecersizGun);
        maasHesabi.setCeza(ceza);
        maasHesabi.setNetMaas(netMaas);

        return maasHesabiRepository.save(maasHesabi);
    }

    @Override
    public boolean deleteMaasHesabi(Long id) {
        if (maasHesabiRepository.existsById(id)) {
            maasHesabiRepository.deleteById(id);
            return true;
        }
        return false;
    }
}