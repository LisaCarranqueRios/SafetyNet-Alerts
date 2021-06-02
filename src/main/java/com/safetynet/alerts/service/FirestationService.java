package com.safetynet.alerts.service;

import com.safetynet.alerts.dao.FirestationDao;
import com.safetynet.alerts.model.Firestation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.List;

@Log4j2
@Service
@Transactional
public class FirestationService implements IFirestationService {

    @Autowired
    FirestationDao firestationDao;

    /**
     * Manage firestations
     *
     * @return
     */
    public List<Firestation> listFirestation() {
        log.debug("List all the firestations");
        return firestationDao.findAll();
    }

    public Firestation displayFirestation(@PathVariable int id) {
        log.debug("Select a firestation by id");
        return firestationDao.findById(id);
    }

    public ResponseEntity<Object> addFirestation(@RequestBody Firestation firestation) {
        Firestation firestationAdded = firestationDao.save(firestation);
        if (firestationAdded == null)
            return ResponseEntity.noContent().build();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(firestationAdded.getId())
                .toUri();
        log.info("Firestation added into database");
        return ResponseEntity.created(location).build();
    }

    public void addFirestations(@RequestBody List<Firestation> firestations) {
        for (Firestation firestation : firestations) {
            Firestation firestationAdded = firestationDao.save(firestation);
        }
        log.info("Firestations added into database");
    }

    public void deleteFirestation(@PathVariable int id) {
        firestationDao.deleteById(id);
        log.info("Firestation removed from database");
    }

    public void updateFirestation(@RequestBody Firestation firestation) {
        firestationDao.save(firestation);
        log.info("Firestation saved into database");
    }

}
