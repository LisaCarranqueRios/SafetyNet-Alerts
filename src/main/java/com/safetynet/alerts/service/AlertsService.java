package com.safetynet.alerts.service;

import com.safetynet.alerts.dao.FirestationDao;
import com.safetynet.alerts.dao.MedicalDao;
import com.safetynet.alerts.dao.PersonDao;
import com.safetynet.alerts.mapper.MapperUtils;
import com.safetynet.alerts.mapper.PersonMapper;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Medical;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.utils.AlertsUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Transactional
@Service
public class AlertsService implements IAlertsService {

    @Autowired
    MedicalDao medicalDao;

    @Autowired
    AlertsUtils alertsUtils;

    @Autowired
    PersonDao personDao;

    @Autowired
    FirestationDao firestationDao;


    /**
     * Method to manage person, firestation, medical CRUD
     *
     */

    /**
     * Manage persons
     *
     * @return
     */
    public List<Person> listPerson() {
        return personDao.findAll();
    }

    public Person displayPerson(@PathVariable int id) {
        return personDao.findById(id);
    }

    public ResponseEntity<Object> addPerson(@RequestBody Person person) {
        Person personAdded = personDao.save(person);
        if (personAdded == null)
            return ResponseEntity.noContent().build();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(personAdded.getId())
                .toUri();
        log.debug("Test something");
        return ResponseEntity.created(location).build();
    }

    public void addPersons(@RequestBody List<Person> persons) {
        for (Person person : persons) {
            List<Medical> medicals = medicalDao.findAll();
            for (Medical medical : medicals) {
                if (medical.getFirstName().equals(person.getFirstName()) && medical.getLastName().equals(person.getLastName()))
                    person.setMedical(medical);
            }
            List<Firestation> firestations = firestationDao.findAll();
            for (Firestation firestation : firestations) {
                if (firestation.getAddress().equals(person.getAddress()))
                    person.setFirestation(firestation);
            }
            Person personAdded = (Person) personDao.save(person);
            log.info("Persons added to database");
        }
    }

    public void deletePerson(@PathVariable int id) {
        personDao.deleteById(id);
        log.info("Person removed from database");
    }

    public void updatePerson(@RequestBody Person person) {
        personDao.save(person);
        log.info("Person updated into database");
    }

    /**
     * Manage firestations
     *
     * @return
     */
    public List<Firestation> listFirestation() {
        log.debug("List all the firestations");
        return firestationDao.findAll();
    }

    public Firestation displayFirestation(@PathVariable int id) {
        log.debug("Select a firestation by id");
        return firestationDao.findById(id);
    }

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

    public void addFirestations(@RequestBody List<Firestation> firestations) {
        for (Firestation firestation : firestations) {
            Firestation firestationAdded = firestationDao.save(firestation);
        }
        log.info("Firestations added into database");
    }

    public void deleteFirestation(@PathVariable int id) {
        firestationDao.deleteById(id);
        log.info("Firestation removed from database");
    }

    public void updateFirestation(@RequestBody Firestation firestation) {
        firestationDao.save(firestation);
        log.info("Firestation saved into database");
    }

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