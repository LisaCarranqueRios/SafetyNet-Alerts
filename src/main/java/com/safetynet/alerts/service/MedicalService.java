package com.safetynet.alerts.service;

import com.safetynet.alerts.dao.MedicalDao;
import com.safetynet.alerts.model.Medical;
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
public class MedicalService implements IMedicalService {

    @Autowired
    MedicalDao medicalDao;

    /**
     * manage medical data
     *
     * @return
     */
    public List<Medical> listMedical() {
        log.debug("List all medical data stored into database");
        return medicalDao.findAll();
    }

    public Medical displayMedical(@PathVariable int id) {
        log.debug("Select a medical data by id");
        return medicalDao.findById(id);
    }

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

    public void addMedicals(@RequestBody List<Medical> medicals) {
        for (Medical medical : medicals) {
            Medical medicalAdded = medicalDao.save(medical);
        }
        log.info("All Medical data saved into database");
    }

    public void deleteMedical(@PathVariable int id) {
        medicalDao.deleteById(id);
        log.info("Medical data removed from database");
    }

    public void updateMedical(@RequestBody Medical medical) {
        medicalDao.save(medical);
        log.info("Medical data saved into database");
    }

}
