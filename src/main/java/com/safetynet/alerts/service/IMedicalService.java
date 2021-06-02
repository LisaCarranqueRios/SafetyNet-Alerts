package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Medical;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IMedicalService {

    /**
     * manage medical
     *
     * @return
     */
    public List<Medical> listMedical();

    public Medical displayMedical(int id);

    public ResponseEntity<Object> addMedical(Medical medical);

    public void addMedicals(List<Medical> medicals);

    public void deleteMedical(int id);

    public void updateMedical(Medical medical);

}
