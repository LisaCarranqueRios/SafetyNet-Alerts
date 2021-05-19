package com.safetynet.alerts.controller;

import com.safetynet.alerts.dao.FirestationDao;
import com.safetynet.alerts.dao.MedicalDao;
import com.safetynet.alerts.dao.PersonDao;
import com.safetynet.alerts.mapper.PersonMapper;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Medical;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.IAlertsService;
import com.safetynet.alerts.utils.AlertsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
public class AlertController {

    @Autowired
    IAlertsService alertsService;

    /**
     * Manage persons
     * @return
     */
    @RequestMapping(value="/persons", method= RequestMethod.GET)
    public List<Person> listPerson() {
        return alertsService.listPerson();
    }

    @GetMapping(value="/persons/{id}")
    public Person displayPerson(@PathVariable int id) {
        return alertsService.displayPerson(id);
    }

    @PostMapping(value = "/person")
    public ResponseEntity<Object> addPerson(@RequestBody Person person) {
        return alertsService.addPerson(person);
    }

    @PostMapping(value = "/persons")
    public void addPersons(@RequestBody List<Person>  persons) {
        alertsService.addPersons(persons);
    }

    @DeleteMapping(value = "/persons/{id}")
    public void delelePerson(@PathVariable int id) {
        alertsService.delelePerson(id);
    }

    @PutMapping (value = "/persons")
    public void updatePerson(@RequestBody Person person) {
        alertsService.updatePerson(person);
    }

    /**
     * Manage firestation
     * @return
     */
    @RequestMapping(value="/firestations", method= RequestMethod.GET)
    public List<Firestation> listFirestation() {
        return alertsService.listFirestation();
    }

    @GetMapping(value="/firestation/{id}")
    public Firestation displayFirestation(@PathVariable int id) {
        return alertsService.displayFirestation(id);
    }

    @PostMapping(value = "/firestation")
    public ResponseEntity<Object> addFirestation(@RequestBody Firestation firestation) {
        return alertsService.addFirestation(firestation);
    }

    @PostMapping(value = "/firestations")
    public void addFirestations(@RequestBody List<Firestation> firestations) {
        alertsService.addFirestations(firestations);
    }

    @DeleteMapping(value = "/firestations/{id}")
    public void deleleFirestation(@PathVariable int id) {
        alertsService.deleleFirestation(id);
    }

    @PutMapping (value = "/firestations")
    public void updateFirestation(@RequestBody Firestation firestation) {
        alertsService.updateFirestation(firestation);
    }

    /**
     * manage medical data
     * @return
     */
    @RequestMapping(value="/medicals", method= RequestMethod.GET)
    public List<Medical> listMedical() {
        return alertsService.listMedical();
    }

    @GetMapping(value="/medicals/{id}")
    public Medical displayMedical(@PathVariable int id) {
        return alertsService.displayMedical(id);
    }

    @PostMapping(value = "/medical")
    public ResponseEntity<Object> addMedical(@RequestBody Medical medical) {
        return alertsService.addMedical(medical);
    }

    @PostMapping(value = "/medicals")
    public void addMedicals(@RequestBody List<Medical> medicals) {
        alertsService.addMedicals(medicals);
    }

    @DeleteMapping(value = "/medicals/{id}")
    public void deleleMedical(@PathVariable int id) {
        alertsService.deleleMedical(id);
    }

    @PutMapping (value = "/medicals")
    public void updateMedical(@RequestBody Medical medical) {
        alertsService.addMedical(medical);
    }

    /**
     * This person is responsible for getting :
     * - email
     * for each person in the targeted city
     * @param city
     * @return
     * @throws IOException
     */
    @GetMapping(value="/communityEmail/{city}")
    public List<PersonMapper> getPersonEmail(@PathVariable("city") String city) {
        return alertsService.getPersonEmail(city);
    }


    /**
     * This person is responsible for getting person information for each person
     * @return
     */
    @GetMapping(value="/personInfo")
    public List<Person> getPersonInformation() {
        return alertsService.getPersonInformation();
    }

    /**
     * This person is responsible for getting
     * person information
     * for a person targeted by lastName and firstName
     * @param names
     * @return
     */
    @GetMapping(value="/personInfo/names")
    public Person getOnePersonInformation(@RequestParam List<String> names) {
        return alertsService.getOnePersonInformation(names);
    }

    /**
     *
     * @param station
     * @param personWithMedication
     * @return
     */
    @GetMapping(value="/flood/{station}")
    public List<Person> getPersonAtAddresse(@PathVariable("station") int station, List<Person> personWithMedication) {
        return alertsService.getPersonAtAddresse(station, personWithMedication);
    }

    /**
     * This method is responsible for getting
     * lastName, firstName, age
     * medical data
     * for person covered by firestations targeted by station number
     * @param ids
     * @return
     */
    @GetMapping(value="/flood/ids")
    public List<Person> getPersonsAtStations(@RequestParam List<Integer> ids) {
        return alertsService.getPersonsAtStations(ids);
    }

    /**
     * This method is responsible for getting :
     * lastName, firstName, phone, age
     * medical data
     * for person covered by firestation targeted by address
     * @param address
     * @return
     */
    //TODO : ajouter un mapper
    @GetMapping(value="/fire/{address}")
    public List<Person>  getPersonAtAddressWithFirestationCoverage(@PathVariable("address") String address) {
        return alertsService.getPersonAtAddressWithFirestationCoverage(address);
    }

    /**
     * This method is responsible for getting :
     * phone number
     * for persons covered by firestation targeted by station number
     * @param station
     * @return
     */
    @GetMapping(value="/phoneAlert/{station}")
    public List<PersonMapper> getPersonCoveredByFirestationPhoneNumber(@PathVariable("station") int station) {
        return alertsService.getPersonCoveredByFirestationPhoneNumber(station);
    }

    /**
     * This method is responsible for getting :
     * firstName, lastName, age
     * and a list of family members
     * for children covered by firestation targeted by one address
     * @param address
     * @return
     */
    //TODO : ajouter un mapper
    @GetMapping(value="/childAlert/{address}")
    public HashMap<List<Person>, List<Person>>  getChildrenAtAddress(@PathVariable("address") String address) {
        return alertsService.getChildrenAtAddress(address);
    }


    /**
     * This method is reponsible for getting :
     * firstName, lastName, phone
     * children and adults count
     * covered by firestations targeted by station number
     * @param station
     * @return
     */
    //TODO : ajouter un mapper
    @GetMapping(value="/firestation/{station}")
    public List<Object> getPersonCoveredByFirestation(@PathVariable("station") int station)   {
        return alertsService.getPersonCoveredByFirestation(station);
    }

}


