package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Firestation;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IFirestationService {


    /**
     * Manage firestations
     *
     * @return
     */
    public List<Firestation> listFirestation();

    public Firestation displayFirestation(int id);

    public ResponseEntity<Object> addFirestation(Firestation firestation);

    public void addFirestations(List<Firestation> firestations);

    public void deleteFirestation(int id);

    public void updateFirestation(Firestation firestation);

}
