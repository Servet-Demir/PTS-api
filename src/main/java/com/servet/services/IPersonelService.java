package com.servet.services;

import java.util.List;

import com.servet.model.Personel;

public interface IPersonelService {

    List<Personel> getAllPersonelList();

    Personel getPersonelById(Long id);

    Personel savePersonel(Personel personel);

    boolean deletePersonel(Long id);

    Personel updatePersonel(Long id, Personel request);
}