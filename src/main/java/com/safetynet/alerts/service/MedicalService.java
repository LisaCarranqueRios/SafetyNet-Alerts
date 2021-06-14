package com.safetynet.alerts.service;

import com.safetynet.alerts.dao.MedicalDao;
import com.safetynet.alerts.exception.MedicalsException;
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
import java.util.ArrayList;
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
        List<Medical> medicals = medicalDao.findAll();
        if(medicals==null||medicals.isEmpty()) throw new MedicalsException("Erreur lors de la récupération" +
                " des dossiers médicaux");
        log.info("Les dossiers médicaux ont bien été récupérés.");
        return medicals;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,  readOnly = true)
    @Override
    public Medical displayMedical(@PathVariable int id) {
        log.debug("Select a medical data by id");
        Medical medical = medicalDao.findById(id);
        if(medical==null) throw new MedicalsException("Erreur lors de la récupération du dossier médical");
        log.info("Le dossier médical "+medical.getId()+ " a bien été récupéré");
        return medical;
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
        List<Medical> medicalOutput = new ArrayList<>();
        for (Medical medical : medicals) {
            Medical medicalAdded = medicalDao.save(medical);
            medicalOutput.add(medicalAdded);
            if (medicalAdded == null) log.error("L'ajout du dossier médical " + medical.getId() + " n'a pas bien fonctionné");
        }
        if (medicalOutput.contains(null)) throw new MedicalsException("Une erreur est survenue durant l'ajout des dossiers médicaux");
        log.info("All Medical data saved into database");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void deleteMedical(@PathVariable int id) {
        medicalDao.deleteById(id);
        if (medicalDao.findById(id)!=null) throw new MedicalsException("Une erreur est survenue durant la suppression du dossier médical");
        log.info("Medical data removed from database");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void updateMedical(@RequestBody Medical medical) {
        Medical medicalSaved = medicalDao.save(medical);
        if (medicalSaved == null) throw new MedicalsException("Une erreur est survenue durant la mise à jour du dossier médical");
        log.info("Medical data saved into database");
    }

}
