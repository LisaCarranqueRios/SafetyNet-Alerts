package com.safetynet.alerts.controller;

import com.safetynet.alerts.mapper.MapperUtils;
import com.safetynet.alerts.mapper.PersonMapper;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Medical;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.IAlertsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@Log4j2
@RestController
public class AlertController {

    @Autowired
    IAlertsService alertsService;


    /*
      Manage person data
     */

    /**
     * This method is responsible for giving a list of the persons stored in the database
     *
     * @return a list of the persons stored in the database
     */
    @GetMapping(value = "/persons")
    public List<Person> listPerson() {
        return alertsService.listPerson();
    }

    /**
     * This method is responsible for selecting a person by id
     *
     * @param id the id of the targeted person
     * @return the person targeted by id
     */
    @GetMapping(value = "/persons/{id}")
    public Person displayPerson(@PathVariable int id) {
        return alertsService.displayPerson(id);
    }

    /**
     * This method is responsible for adding a person in the database
     *
     * @param person the person to add in the database
     * @return a 201 code with saved object url
     */
    @PostMapping(value = "/person")
    public ResponseEntity<Object> addPerson(@RequestBody Person person) {
        return alertsService.addPerson(person);
    }

    /**
     * This method is responsible for adding a list of persons in the database
     *
     * @param persons the list of persons to add in the database
     */
    @PostMapping(value = "/persons")
    public void addPersons(@RequestBody List<Person> persons) {
        alertsService.addPersons(persons);
    }

    /**
     * This method is responsible for removing a person from database
     *
     * @param id the id of the selected person to remove
     */
    @DeleteMapping(value = "/persons/{id}")
    public void deletePerson(@PathVariable int id) {
        alertsService.deletePerson(id);
    }

    /**
     * This method is responsible for updating person data in the database
     *
     * @param person the person to update in the database
     */
    @PutMapping(value = "/persons")
    public void updatePerson(@RequestBody Person person) {
        alertsService.updatePerson(person);
    }

    /*
    Manage firestation data
     */

    /**
     * This method is responsible for listing all firestations saved in the database
     * @return a list of firestations saved in the database
     */
    @RequestMapping(value = "/firestations", method = RequestMethod.GET)
    public List<Firestation> listFirestation() {
        return alertsService.listFirestation();
    }

    /**
     * This method is responsible for fetching a firestation selected by id in the database
     * @param id the id of the selected firestation
     * @return the firestation selected by id
     */
    @GetMapping(value = "/firestations/{id}")
    public Firestation displayFirestation(@PathVariable int id) {
        return alertsService.displayFirestation(id);
    }

    /**
     * This method is responsible for adding a firestation in the database
     * @param firestation the firestation instance to add in the database
     * @return a 201 saved code
     */
    @PostMapping(value = "/firestation")
    public ResponseEntity<Object> addFirestation(@RequestBody Firestation firestation) {
        return alertsService.addFirestation(firestation);
    }

    /**
     * This method is responsible for adding a list of firestations in the database
     * @param firestations the list of firestations intance to add in the database
     */
    @PostMapping(value = "/firestations")
    public void addFirestations(@RequestBody List<Firestation> firestations) {
        alertsService.addFirestations(firestations);
    }

    /**
     * This method is responsible for deleting a firestation targeted by id in the database
     * @param id the id of the firestation to remove from the database
     */
    @DeleteMapping(value = "/firestations/{id}")
    public void deleteFirestation(@PathVariable int id) {
        alertsService.deleteFirestation(id);
    }

    /**
     * This method is responsible for updating a firestation in the database
     * @param firestation  the firestation to update in the database
     */
    @PutMapping(value = "/firestations")
    public void updateFirestation(@RequestBody Firestation firestation) {
        alertsService.updateFirestation(firestation);
    }

    /*
    manage medical data
     */

    /**
     * This method is responsible for listing all the medical records saved in the database
     * @return
     */
    @RequestMapping(value = "/medicals", method = RequestMethod.GET)
    public List<Medical> listMedical() {
        return alertsService.listMedical();
    }

    /**
     * This method is responsible for fetching a medical record stored in the dabase selected by id
     * @param id
     * @return
     */
    @GetMapping(value = "/medicals/{id}")
    public Medical displayMedical(@PathVariable int id) {
        return alertsService.displayMedical(id);
    }

    /**
     * This method is responsible for adding a medical record in the database
     * @param medical
     * @return
     */
    @PostMapping(value = "/medical")
    public ResponseEntity<Object> addMedical(@RequestBody Medical medical) {
        return alertsService.addMedical(medical);
    }

    /**
     * This method is responsible for adding a list of medical records in the database
     * @param medicals
     */
    @PostMapping(value = "/medicals")
    public void addMedicals(@RequestBody List<Medical> medicals) {
        alertsService.addMedicals(medicals);
    }

    /**
     * This method is responsible for deleting a medical record selected by id
     * @param id
     */
    @DeleteMapping(value = "/medicals/{id}")
    public void deleteMedical(@PathVariable int id) {
        alertsService.deleteMedical(id);
    }

    /**
     * This method is reponsible for updating medical record data for a medical record.
     * @param medical
     */
    @PutMapping(value = "/medicals")
    public void updateMedical(@RequestBody Medical medical) {
        alertsService.updateMedical(medical);
    }

    /**
     * This person is responsible for getting :
     * - email
     * for each person in the targeted city
     *
     * @param city the selected city
     * @return a list of email from person in this city
     * @throws IOException
     */
    @GetMapping(value = "/communityEmail/{city}")
    public List<String> getPersonEmail(@PathVariable("city") String city) {
        return alertsService.getPersonEmail(city);
    }

    /**
     * This method is responsible for getting
     * - person information
     * for a person targeted by lastName and firstName
     *
     * @param firstName the firtName of the selected person
     * @param lastName the lastName of the selected person
     * @return person information for the selected person
     */
    @RequestMapping(value = "/personInfo")
    public List<PersonMapper> getOnePersonInformationByNames(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName) {
        List<Person> persons = alertsService.getPersonInformationByNames(firstName, lastName);
        return MapperUtils.getOnePersonInformationByNamesMapper(persons);
    }

    /**
     * This method is responsible for getting
     * - lastName, firstName, age
     * - medical data
     * for persons covered by the firestations targeted by station number
     *
     * @param ids the liste of stations id
     * @return a list of these persons information and their medical data
     */
    @GetMapping(value = "/flood/ids")
    public List<PersonMapper> getPersonsAtStations(@RequestParam List<Integer> ids) {
        List<Person> persons = alertsService.getPersonsAtStations(ids);
        return MapperUtils.getPersonsInformationAndMedicalDataMapper(persons);
    }

    /**
     * This method is responsible for getting :
     * - lastName, firstName, phone, age
     * - medical data
     * for persons covered by the firestations targeted by address
     *
     * @param address the address of the selected firestations
     * @return a list of these persons information and their medical data
     */
    @GetMapping(value = "/fire/{address}")
    public List<PersonMapper> getPersonAtAddressWithFirestationCoverage(@PathVariable("address") String address) {
        List<Person> persons = alertsService.getPersonAtAddressWithFirestationCoverage(address);
        return MapperUtils.getPersonsInformationAndMedicalDataMapper(persons);
    }

    /**
     * This method is responsible for getting :
     * - phone number
     * for persons covered by  the firestations targeted by station number
     * @param station the number of the selected station
     * @return a list of phone number for these persons
     */
    @GetMapping(value = "/phoneAlert/{station}")
    public List<PersonMapper> getPersonCoveredByFirestationPhoneNumber(@PathVariable("station") int station) {
        List<Person> persons = alertsService.getPersonCoveredByFirestationPhoneNumber(station);
        return MapperUtils.getPersonCoveredByFirestationPhoneNumberMapper(persons);
    }

    /**
     * This method is responsible for getting :
     * - firstName, lastName, age
     * - a list of house members
     * for children covered by the firestations targeted by address
     *
     * @param address the selected address
     * @return the list of children with their house members
     */
    @GetMapping(value = "/childAlert/{address}")
    public List<Object> getChildrenAtAddress(@PathVariable("address") String address) {
        return alertsService.getChildrenAtAddress(address);
    }


    /**
     * This method is responsible for getting :
     * - firstName, lastName, phone
     * - children and adults count
     * for the persons covered by the firestations targeted by station number
     *
     * @param station the selected station number
     * @return the list person information 
     */
    @GetMapping(value = "/firestation")
    public List<Object> getPersonCoveredByFirestation(@RequestParam("stationNumber") int station) {
        return alertsService.getPersonCoveredByFirestation(station);
    }

}


