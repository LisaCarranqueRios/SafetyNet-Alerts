package com.safetynet.alerts.service;

import com.safetynet.alerts.dao.PersonDao;
import com.safetynet.alerts.utils.MapperUtils;
import com.safetynet.alerts.mapper.PersonMapper;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.utils.AlertsUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@Transactional
public class AlertsService implements IAlertsService {

    @Autowired
    AlertsUtils alertsUtils;

    @Autowired
    PersonDao personDao;


    /**
     * Manage person, firestation, medical data to dispay targeted information from url endpoints
     */

    /**
     * This person is responsible for getting :
     * - email
     * for each person in the targeted city
     *
     * @param city
     * @return
     */
    @Override
    public List<String> getPersonEmail(@PathVariable("city") String city) {
        List<String> personEmail = personDao.getPersonEmail(city);
        log.info("List all person emails from city " + city);
        return personEmail;
    }

    /**
     * This person is responsible for getting
     * person information
     * for a person targeted by lastName and firstName
     *
     * @return
     */
    @Override
    public List<Person> getPersonInformationByNames(@RequestParam String firstName, @RequestParam String lastName) {
        List<Person> persons = personDao.findByFirstNameAndLastName(firstName, lastName);
        for (Person person : persons) {
            person.setAge(alertsUtils.getAge(person.getMedical().getBirthdate()));
        }
        log.info("Fetch person by firstname and lastname :" + firstName + " " + lastName);
        return persons;
    }

    /**
     * @param station
     * @return
     */
    @Override
    public List<Person> getPersonAtAddress(@PathVariable("station") int station) {
        List<Person> personCovered = personDao.findByStation(station);
        for (Person person : personCovered) {
            person.setAge(alertsUtils.getAge(person.getMedical().getBirthdate()));
        }
        log.info("Fetch person at address covered by firestation with station number" + station);
        return personCovered;
    }

    /**
     * This method is responsible for getting
     * lastName, firstName, age
     * medical data
     * for person covered by firestations targeted by station number
     *
     * @param ids
     * @return
     */
    @Override
    public List<Person> getPersonsAtStations(@RequestParam List<Integer> ids) {
        List<Person> personWithMedication = new ArrayList<>();
        for (int id : ids) {
            personWithMedication.addAll(getPersonAtAddress(id));
        }
        return personWithMedication;
    }

    /**
     * This method is responsible for getting :
     * lastName, firstName, phone, age
     * medical data
     * for person covered by firestation targeted by address
     *
     * @param address
     * @return
     */
    @Override
    public List<Person> getPersonAtAddressWithFirestationCoverage(@PathVariable("address") String address) {
        List<Person> personCovered = personDao.findByAddress(address);
        for (Person person : personCovered) {
            person.setStation(person.getFirestation().getStation());
            person.setAge(AlertsUtils.getAge(person.getMedical().getBirthdate()));
        }
        log.info("Fetch person information and their medical data at address " + address);
        return personCovered;
    }

    /**
     * This method is responsible for getting :
     * phone number
     * for persons covered by firestation targeted by station number
     *
     * @param station
     * @return
     */
    @Override
    public List<Person> getPersonCoveredByFirestationPhoneNumber(@PathVariable("station") int station) {
        List<Person> personCovered = personDao.findByStation(station);
        log.info("List phone numbers for persons covered by firestation with station number " + station);
        return personCovered;
    }

    /**
     * This method is responsible for getting :
     * firstName, lastName, age
     * and a list of house members
     * for children covered by firestation targeted by one address
     *
     * @param address
     * @return
     */
    @Override
    public List<Object> getChildrenAtAddress(@PathVariable("address") String address) {
        List<Person> personCovered = personDao.findByAddress(address);
        for (Person person : personCovered) {
            person.setStation(person.getFirestation().getStation());
        }
        log.info("Fetch children information and their house members at address" + address);
        return alertsUtils.listChildAndHouse(personCovered);
    }


    /***
     * This method is responsible for getting :
     * - firstName, lastName, phone
     * - children and adults count
     * for the persons covered by the firestations targeted by station number
     * @param station
     * @return
     */
    @Override
    public List<Object> getPersonCoveredByFirestation(@PathVariable("station") int station) {
        List<Object> personCoveredByFirestation = new ArrayList<>();
        List<Person> personCovered = personDao.findByStation(station);
        for (Person person : personCovered) {
            person.setStation(station);
        }
        List<PersonMapper> personCoveredMapper = MapperUtils.getPersonCoveredByFirestationMapper(personCovered);
        List<Object> count = alertsUtils.getPersonCount(personCovered);
        personCoveredByFirestation.add(personCoveredMapper);
        personCoveredByFirestation.add(count);
        log.info("Get person with adult and child count covered by firestation with station number "+ station);
        return personCoveredByFirestation;
    }

}