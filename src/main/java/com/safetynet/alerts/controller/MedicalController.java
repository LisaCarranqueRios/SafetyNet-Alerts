package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.Medical;
import com.safetynet.alerts.service.IMedicalService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
public class MedicalController {

    @Autowired
    IMedicalService medicalService;

    /*
    manage medical data
     */

    /**
     * This method is responsible for listing all the medical records saved in the database
     * @return
     */
    @RequestMapping(value = "/medicals", method = RequestMethod.GET)
    public List<Medical> listMedical() {
        return medicalService.listMedical();
    }

    /**
     * This method is responsible for fetching a medical record stored in the dabase selected by id
     * @param id
     * @return
     */
    @GetMapping(value = "/medicals/{id}")
    public Medical displayMedical(@PathVariable int id) {
        return medicalService.displayMedical(id);
    }

    /**
     * This method is responsible for adding a medical record in the database
     * @param medical
     * @return
     */
    @PostMapping(value = "/medical")
    public ResponseEntity<Object> addMedical(@RequestBody Medical medical) {
        return medicalService.addMedical(medical);
    }

    /**
     * This method is responsible for adding a list of medical records in the database
     * @param medicals
     */
    @PostMapping(value = "/medicals")
    public void addMedicals(@RequestBody List<Medical> medicals) {
        medicalService.addMedicals(medicals);
    }

    /**
     * This method is responsible for deleting a medical record selected by id
     * @param id
     */
    @DeleteMapping(value = "/medicals/{id}")
    public void deleteMedical(@PathVariable int id) {
        medicalService.deleteMedical(id);
    }

    /**
     * This method is reponsible for updating medical record data for a medical record.
     * @param medical
     */
    @PutMapping(value = "/medicals")
    public void updateMedical(@RequestBody Medical medical) {
        medicalService.updateMedical(medical);
    }


}
