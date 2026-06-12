package com.servet.services;

import java.util.List;

import com.servet.model.Birim;

public interface IBirimService {

    List<Birim> getAllBirimList();

    Birim getBirimById(Long id);

    Birim saveBirim(Birim birim);

    boolean deleteBirim(Long id);

    Birim updateBirim(Long id, Birim request);
}