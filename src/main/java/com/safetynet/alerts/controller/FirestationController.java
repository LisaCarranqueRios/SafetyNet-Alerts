package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.service.IFirestationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
public class FirestationController {

    @Autowired
    IFirestationService firestationService;

    /*
    Manage firestation data
     */

    /**
     * This method is responsible for listing all firestations saved in the database
     * @return a list of firestations saved in the database
     */
    @RequestMapping(value = "/firestations", method = RequestMethod.GET)
    public List<Firestation> listFirestation() {
        return firestationService.listFirestation();
    }

    /**
     * This method is responsible for fetching a firestation selected by id in the database
     * @param id the id of the selected firestation
     * @return the firestation selected by id
     */
    @GetMapping(value = "/firestations/{id}")
    public Firestation displayFirestation(@PathVariable int id) {
        return firestationService.displayFirestation(id);
    }

    /**
     * This method is responsible for adding a firestation in the database
     * @param firestation the firestation instance to add in the database
     * @return a 201 saved code
     */
    @PostMapping(value = "/firestation")
    public ResponseEntity<Object> addFirestation(@RequestBody Firestation firestation) {
        return firestationService.addFirestation(firestation);
    }

    /**
     * This method is responsible for adding a list of firestations in the database
     * @param firestations the list of firestations intance to add in the database
     */
    @PostMapping(value = "/firestations")
    public void addFirestations(@RequestBody List<Firestation> firestations) {
        firestationService.addFirestations(firestations);
    }

    /**
     * This method is responsible for deleting a firestation targeted by id in the database
     * @param id the id of the firestation to remove from the database
     */
    @DeleteMapping(value = "/firestations/{id}")
    public void deleteFirestation(@PathVariable int id) {
        firestationService.deleteFirestation(id);
    }

    /**
     * This method is responsible for updating a firestation in the database
     * @param firestation  the firestation to update in the database
     */
    @PutMapping(value = "/firestations")
    public void updateFirestation(@RequestBody Firestation firestation) {
        firestationService.updateFirestation(firestation);
    }

}
