package com.safetynet.alerts.service;

import com.safetynet.alerts.dao.FirestationDao;
import com.safetynet.alerts.exception.FirestationsException;
import com.safetynet.alerts.model.Firestation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class FirestationService implements IFirestationService {

    @Autowired
    FirestationDao firestationDao;

    /**
     * Manage firestations
     *
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,  readOnly = true)
    @Override
    public List<Firestation> listFirestation() {
        log.debug("List all the firestations");
        List<Firestation> firestations = firestationDao.findAll();
        if(firestations==null||firestations.isEmpty()) throw new FirestationsException("Erreur lors de la récupération des casernes");
        log.info("All firestations : "+ firestations);
        return firestations;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,  readOnly = true)
    @Override
    public Firestation displayFirestation(@PathVariable int id) {
        log.debug("Select a firestation by id");
        Firestation firestation = firestationDao.findById(id);
        if(firestation==null) throw new FirestationsException("Erreur lors de la récupération de la caserne");
        log.info("Targeted firestation : "+ firestation);
        return firestation;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
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

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void addFirestations(@RequestBody List<Firestation> firestations) {
        List<Firestation> firestationsOutput = new ArrayList<>();
        for (Firestation firestation : firestations) {
            Firestation firestationSaved = firestationDao.save(firestation);
            firestationsOutput.add(firestationSaved);
            if (firestationSaved == null) log.error("L'ajout de la caserne " + firestation.getId() + " n'a pas bien fonctionné");
        }
        if (firestationsOutput.contains(null)) throw new FirestationsException("Une erreur est survenue durant l'ajout des casernes");
        log.info("Firestations added into database");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void deleteFirestation(@PathVariable int id) {
        firestationDao.deleteById(id);
        if (firestationDao.findById(id)!=null) throw new FirestationsException("Une erreur est survenue durant la suppression de la caserne");
        log.info("Firestation removed from database");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void updateFirestation(@RequestBody Firestation firestation) {
        Firestation firestationSaved = firestationDao.save(firestation);
        if (firestationSaved == null) throw new FirestationsException("Une erreur est survenue durant la mise à jour de la caserne");
        log.info("Firestation saved into database");
    }

}
