package com.servet.services.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servet.model.MesaiKaydi;
import com.servet.model.Personel;
import com.servet.repository.MesaiKaydiRepository;
import com.servet.services.IMesaiKaydiService;
import com.servet.services.IPersonelService;

@Service
public class MesaiKaydiServiceImpl implements IMesaiKaydiService {

    @Autowired
    private MesaiKaydiRepository mesaiKaydiRepository;

    @Autowired
    private IPersonelService personelService;

    private static final LocalTime MESAI_BASLANGIC = LocalTime.of(9, 0);
    private static final LocalTime MESAI_BITIS = LocalTime.of(18, 0);
    private static final int TOLERANS_DAKIKA = 15;

    @Override
    public List<MesaiKaydi> getAllMesaiList() {
        return mesaiKaydiRepository.findAll();
    }

    @Override
    public List<MesaiKaydi> getMesaiByTarih(LocalDate tarih) {
        return mesaiKaydiRepository.findByTarih(tarih);
    }

    @Override
    public List<MesaiKaydi> getMesaiByBirimAndTarih(Long birimId, LocalDate tarih) {
        return mesaiKaydiRepository.findByPersonel_BirimBirimIdAndTarih(birimId, tarih);
    }

    @Override
    public MesaiKaydi saveMesai(MesaiKaydi mesaiKaydi) {
        Personel personel = personelService.getPersonelById(
            mesaiKaydi.getPersonel().getPersonelId()
        );
        if (personel == null) {
            return null;
        }
        mesaiKaydi.setPersonel(personel);
        mesaiKaydi.setMesaiGecerli(hesaplaGecerlilik(personel, mesaiKaydi));
        return mesaiKaydiRepository.save(mesaiKaydi);
    }

    @Override
    public MesaiKaydi updateMesai(Long id, MesaiKaydi request) {
        MesaiKaydi mesaiKaydi = mesaiKaydiRepository.findById(id).orElse(null);
        if (mesaiKaydi != null) {
            mesaiKaydi.setGirisSaati(request.getGirisSaati());
            mesaiKaydi.setCikisSaati(request.getCikisSaati());
            mesaiKaydi.setMesaiGecerli(
                hesaplaGecerlilik(mesaiKaydi.getPersonel(), mesaiKaydi)
            );
            return mesaiKaydiRepository.save(mesaiKaydi);
        }
        return null;
    }

    @Override
    public boolean deleteMesai(Long id) {
        if (mesaiKaydiRepository.existsById(id)) {
            mesaiKaydiRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private boolean hesaplaGecerlilik(Personel personel, MesaiKaydi mesaiKaydi) {
        if (personel.getYonetici()) {
            return true;
        }
        if (mesaiKaydi.getGirisSaati() == null || mesaiKaydi.getCikisSaati() == null) {
            return false;
        }
        int girisFark = (int) java.time.Duration.between(
            MESAI_BASLANGIC, mesaiKaydi.getGirisSaati()
        ).toMinutes();
        int cikisFark = (int) java.time.Duration.between(
            mesaiKaydi.getCikisSaati(), MESAI_BITIS
        ).toMinutes();
        int toplamEksik = 0;
        if (girisFark > 0) toplamEksik += girisFark;
        if (cikisFark > 0) toplamEksik += cikisFark;
        return toplamEksik <= TOLERANS_DAKIKA;
    }
    
    @Override
    public List<MesaiKaydi> getMesaiByPersonelAndAy(Long personelId, LocalDate donem) {
        LocalDate baslangic = donem.withDayOfMonth(1);
        LocalDate bitis = donem.withDayOfMonth(donem.lengthOfMonth());
        return mesaiKaydiRepository.findByPersonel_PersonelIdAndTarihBetween(personelId, baslangic, bitis);
    }
}