package com.servet.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servet.entities.MesaiKaydi;
import com.servet.entities.Personel;
import com.servet.repository.MesaiKaydiRepository;

@Service
public class MesaiKaydiService {

    @Autowired
    private MesaiKaydiRepository mesaiKaydiRepository;

    @Autowired
    private PersonelService personelService;

    private static final LocalTime mesaiBaslangic = LocalTime.of(9, 0);
    private static final LocalTime mesaiBitis = LocalTime.of(18, 0);
    private static final int toleransDakika = 15;

    public List<MesaiKaydi> getAllMesaiList() {
        return mesaiKaydiRepository.findAll();
    }

    public List<MesaiKaydi> getMesaiByTarih(LocalDate tarih) {
        return mesaiKaydiRepository.findByTarih(tarih);
    }

    public MesaiKaydi saveMesai(MesaiKaydi mesaiKaydi) {
        Personel personel = personelService.getPersonelById(
                mesaiKaydi.getPersonel().getPersonelId());
        if (personel == null) {
            return null;
        }
        mesaiKaydi.setPersonel(personel);
        mesaiKaydi.setMesaiGecerli(hesaplaGecerlilik(personel, mesaiKaydi));
        return mesaiKaydiRepository.save(mesaiKaydi);
    }

    public MesaiKaydi updateMesai(Long id, MesaiKaydi request) {
        MesaiKaydi mesaiKaydi = mesaiKaydiRepository.findById(id).orElse(null);
        if (mesaiKaydi != null) {
            mesaiKaydi.setGirisSaati(request.getGirisSaati());
            mesaiKaydi.setCikisSaati(request.getCikisSaati());
            mesaiKaydi.setMesaiGecerli(
                    hesaplaGecerlilik(mesaiKaydi.getPersonel(), mesaiKaydi));
            return mesaiKaydiRepository.save(mesaiKaydi);
        }
        return null;
    }

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
                mesaiBaslangic, mesaiKaydi.getGirisSaati()).toMinutes();
        int cikisFark = (int) java.time.Duration.between(
                mesaiKaydi.getCikisSaati(), mesaiBitis).toMinutes();
        int toplamEksik = 0;
        if (girisFark > 0)
            toplamEksik += girisFark;
        if (cikisFark > 0)
            toplamEksik += cikisFark;
        return toplamEksik <= toleransDakika;
    }
}