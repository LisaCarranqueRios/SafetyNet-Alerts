package com.safetynet.alerts.controller;

import com.safetynet.alerts.mapper.ChildMapper;
import com.safetynet.alerts.mapper.CountMapper;
import com.safetynet.alerts.utils.MapperUtils;
import com.safetynet.alerts.mapper.PersonMapper;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.IAlertsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@Log4j2
@RestController
public class AlertController {

    @Autowired
    IAlertsService alertsService;

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
    public ChildMapper getChildrenAtAddress(@PathVariable("address") String address) {
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
    public CountMapper getPersonCoveredByFirestation(@RequestParam("stationNumber") int station) {
        return alertsService.getPersonCoveredByFirestation(station);
    }

}


