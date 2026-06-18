package com.servet.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servet.entities.MesaiKaydi;
import com.servet.entities.Personel;
import com.servet.repository.MesaiKaydiRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
        log.info("  Fetching all attendance records.");
        return mesaiKaydiRepository.findAll();
    }

    public List<MesaiKaydi> getMesaiByTarih(LocalDate tarih) {
        log.info("Fetching attendance records by date: {}", tarih);
        return mesaiKaydiRepository.findByTarih(tarih);
    }

    public MesaiKaydi saveMesai(MesaiKaydi mesaiKaydi) {
        log.info("Attendance record creation process started.");
        Personel personel = personelService.getPersonelById(
                mesaiKaydi.getPersonel().getPersonelId());

        if (personel == null) {
            log.warn("Attendance record could not be created. Employee not found.");
            return null;
        }

        log.info("Employee found for attendance record. Employee ID: {}", personel.getPersonelId());
        mesaiKaydi.setPersonel(personel);
        log.info("Employee assigned to attendance record. Employee ID: {}", personel.getPersonelId());
        mesaiKaydi.setMesaiGecerli(hesaplaGecerlilik(personel, mesaiKaydi));

        log.info("Attendance validity calculated. Employee ID: {}, Valid: {}",
                personel.getPersonelId(),
                mesaiKaydi.getMesaiGecerli());

        log.info("Attendance record is being saved. Employee ID: {}", personel.getPersonelId());
        return mesaiKaydiRepository.save(mesaiKaydi);
    }

    public MesaiKaydi updateMesai(Long id, MesaiKaydi request) {
        log.info("Attendance record update process started. Attendance ID: {}", id);
        MesaiKaydi mesaiKaydi = mesaiKaydiRepository.findById(id).orElse(null);

        if (mesaiKaydi != null) {
            log.info("Attendance record found. Attendance ID: {}", id);

            mesaiKaydi.setGirisSaati(request.getGirisSaati());
            mesaiKaydi.setCikisSaati(request.getCikisSaati());

            log.info("Entry and exit times updated. Attendance ID: {}", id);

            mesaiKaydi.setMesaiGecerli(
                    hesaplaGecerlilik(mesaiKaydi.getPersonel(), mesaiKaydi));

            log.info("Attendance validity recalculated. Attendance ID: {}, Valid: {}",
                    id,
                    mesaiKaydi.getMesaiGecerli());

            log.info("Attendance record is being saved after update. Attendance ID: {}", id);

            return mesaiKaydiRepository.save(mesaiKaydi);
        }
        log.warn("Attendance record could not be updated. Record not found. Attendance ID: {}", id);
        return null;
    }

    public boolean deleteMesai(Long id) {
        log.info("Attendance record deletion process started. Attendance ID: {}", id);
        if (mesaiKaydiRepository.existsById(id)) {
            mesaiKaydiRepository.deleteById(id);

            log.info("Attendance record deleted successfully. Attendance ID: {}", id);

            return true;
        }
        log.warn("Attendance record could not be deleted. Record not found. Attendance ID: {}", id);

        return false;
    }

    private boolean hesaplaGecerlilik(Personel personel, MesaiKaydi mesaiKaydi) {
        log.info("Attendance validity calculation started. Employee ID: {}", personel.getPersonelId());

        if (personel.getYonetici()) {
            log.info("Employee is a manager. Attendance is automatically valid. Employee ID: {}",
                    personel.getPersonelId());
            return true;
        }

        if (mesaiKaydi.getGirisSaati() == null || mesaiKaydi.getCikisSaati() == null) {
            log.warn("Attendance is invalid. Entry or exit time is missing. Employee ID: {}",
                    personel.getPersonelId());
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
        log.info(
                "Attendance missing time calculated. Employee ID: {}, Late Minutes: {}, Early Leave Minutes: {}, Total Missing Minutes: {}",
                personel.getPersonelId(),
                girisFark,
                cikisFark,
                toplamEksik);
        return toplamEksik <= toleransDakika;
    }
}