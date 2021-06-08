package com.safetynet.alerts.service;

import com.safetynet.alerts.dao.MedicalDao;
import com.safetynet.alerts.model.Medical;
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
import java.util.List;

@Log4j2
@Service
public class MedicalService implements IMedicalService {

    @Autowired
    MedicalDao medicalDao;

    /**
     * manage medical data
     *
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,  readOnly = true)
    @Override
    public List<Medical> listMedical() {
        log.debug("List all medical data stored into database");
        return medicalDao.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,  readOnly = true)
    @Override
    public Medical displayMedical(@PathVariable int id) {
        log.debug("Select a medical data by id");
        return medicalDao.findById(id);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public ResponseEntity<Object> addMedical(@RequestBody Medical medical) {
        Medical medicalAdded = medicalDao.save(medical);
        if (medicalAdded == null)
            return ResponseEntity.noContent().build();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(medicalAdded.getId())
                .toUri();
        log.info("Medical data saved into database");
        return ResponseEntity.created(location).build();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void addMedicals(@RequestBody List<Medical> medicals) {
        for (Medical medical : medicals) {
            Medical medicalAdded = medicalDao.save(medical);
        }
        log.info("All Medical data saved into database");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void deleteMedical(@PathVariable int id) {
        medicalDao.deleteById(id);
        log.info("Medical data removed from database");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void updateMedical(@RequestBody Medical medical) {
        medicalDao.save(medical);
        log.info("Medical data saved into database");
    }

}
