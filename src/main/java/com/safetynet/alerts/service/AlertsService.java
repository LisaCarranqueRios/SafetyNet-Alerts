package com.safetynet.alerts.service;

import com.safetynet.alerts.dao.FirestationDao;
import com.safetynet.alerts.dao.MedicalDao;
import com.safetynet.alerts.dao.PersonDao;
import com.safetynet.alerts.mapper.PersonMapper;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Medical;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.utils.AlertsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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
        return ResponseEntity.created(location).build();
    }

    public void addPersons(@RequestBody List<Person>  persons) {
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
        }
    }

    public void delelePerson(@PathVariable int id) {
        personDao.deleteById(id);
    }

    public void updatePerson(@RequestBody Person person) {

        personDao.save(person);
    }

    /**
     * Manage firestations
     * @return
     */
    public List<Firestation> listFirestation() {
        return firestationDao.findAll();
    }

    public Firestation displayFirestation(@PathVariable int id) {
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
        return ResponseEntity.created(location).build();
    }

    public void addFirestations(@RequestBody List<Firestation> firestations) {
        for (Firestation firestation : firestations) {
            Firestation firestationAdded = firestationDao.save(firestation);
        }
    }

    public void deleleFirestation(@PathVariable int id) {
        firestationDao.deleteById(id);
    }

    public void updateFirestation(@RequestBody Firestation firestation) {
        firestationDao.save(firestation);
    }

    /**
     * manage medical data
     * @return
     */
    public List<Medical> listMedical() {
        return medicalDao.findAll();
    }

    public Medical displayMedical(@PathVariable int id) {
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
        return ResponseEntity.created(location).build();
    }

    public void addMedicals(@RequestBody List<Medical> medicals) {
        for (Medical medical : medicals) {
            Medical medicalAdded = medicalDao.save(medical);
        }
    }

    public void deleleMedical(@PathVariable int id) {
        medicalDao.deleteById(id);
    }

    public void updateMedical(@RequestBody Medical medical) {
        medicalDao.save(medical);
    }

    /**
     * Manage person, firestation, medical data to dispay targeted information from url endpoints
     */

    /**
     * This person is responsible for getting :
     * - email
     * for each person in the targeted city
     * @param city
     * @return
     */
    @Override
    public List<PersonMapper> getPersonEmail(@PathVariable("city") String city)  {
        List<Person> personEmail = personDao.getPersonEmail("Culver");
        List<PersonMapper> personMappers = new ArrayList<>();
        Iterator<Person> personEmailIterator =  personEmail.iterator();
        while(personEmailIterator.hasNext()) {
            Person personObject = personEmailIterator.next();
            PersonMapper person = new PersonMapper();
            person.setFirstName(String.valueOf(personObject.getFirstName()));
            person.setLastName(String.valueOf(personObject.getLastName()));
            person.setEmail(String.valueOf(personObject.getEmail()));
            personMappers.add(person);
        }
        return personMappers;
    }

    /**
     * This person is responsible for getting person information for each person
     * @return
     */
    @Override
    public List<Person> getPersonInformation() {
        HashMap<Integer, List<Object>> personWithMedication = new HashMap<>();
        List<Person> persons = personDao.findAll();
        for (Person person : persons) {
            person.setAge(alertsUtils.getAge(person.getMedical().getBirthdate()));
        }
        return persons;
    }

    /**
     * This person is responsible for getting
     * person information
     * for a person targeted by lastName and firstName
     * @param names
     * @return
     */
    @Override
    public Person getOnePersonInformation(@RequestParam List<String> names) {
        List<Person> persons = personDao.findAll();
         for (Person person : persons) {
            if (names.contains(person.getLastName()) && names.contains(person.getFirstName())) {
                person.setAge(alertsUtils.getAge(person.getMedical().getBirthdate()));
                return person;
            }
        }
        return null;
    }

    @Override
    public List<Person> getPersonAtAddresse(@PathVariable("station") int station, List<Person> personWithMedication) {
        List<Person> persons = personDao.findAll();
        List<Person> personCovered = new ArrayList<>();
         for (Person person : persons) {
            if (person.getFirestation().getStation() == station ) {
                person.setAge(alertsUtils.getAge(person.getMedical().getBirthdate()));
                personCovered.add(person);
            }
        } return personCovered;
    }

    /**
     * This method is responsible for getting
     * lastName, firstName, age
     * medical data
     * for person covered by firestations targeted by station number
     * @param ids
     * @return
     */
    @Override
    public List<Person> getPersonsAtStations(@RequestParam List<Integer> ids) {
        List<Person>  personWithMedication = new ArrayList<>();
        for (int id : ids) {
            personWithMedication.addAll(getPersonAtAddresse(id, personWithMedication));
        }
        return personWithMedication;
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
    @Override
    public List<Person>  getPersonAtAddressWithFirestationCoverage(@PathVariable("address") String address) {
        List<Person> persons = personDao.findAll();
        List<Firestation> firestations = firestationDao.findByAddress(address);
        List<Person> personCovered = new ArrayList<>();
         for (Firestation firestation :  firestations) {
            for (Person person : persons) {
                if (person.getAddress().equals(firestation.getAddress())) {
                    person.setStation(firestation.getStation());
                    person.setAge(AlertsUtils.getAge(person.getMedical().getBirthdate()));
                    personCovered.add(person);
                }
            }
        } return personCovered;
    }

    /**
     * This method is responsible for getting :
     * phone number
     * for persons covered by firestation targeted by station number
     * @param station
     * @return
     */
    @Override
    public List<PersonMapper> getPersonCoveredByFirestationPhoneNumber(@PathVariable("station") int station) {
        List<Person> persons = personDao.findAll();
        List<Firestation> firestations = firestationDao.findByStation(station);
        List<Person> personCovered = new ArrayList<>();
         for (Firestation firestation : firestations) {
            for (Person person : persons) {
                if (person.getAddress().equals(firestation.getAddress())) {
                    person.setStation(firestation.getStation());
                    personCovered.add(person);
                }
            }
        }
         List<PersonMapper> personMappers = new ArrayList<>();
        Iterator<Person> personEmailIterator =  personCovered.iterator();
        while(personEmailIterator.hasNext()) {
            Person person = personEmailIterator.next();
            PersonMapper personMapper = new PersonMapper();
            personMapper.setFirstName(String.valueOf(person.getFirstName()));
            personMapper.setLastName(String.valueOf(person.getLastName()));
            personMapper.setPhone(String.valueOf(person.getPhone()));
            personMapper.setStation(String.valueOf(person.getStation()));
            personMappers.add(personMapper);
        }
        return personMappers;
    }

    /**
     * This method is responsible for getting :
     * firstName, lastName, age
     * and a list of family members
     * for children covered by firestation targeted by one address
     * @param address
     * @return
     */
     @Override
    public HashMap<List<Person>, List<Person>>  getChildrenAtAddress(@PathVariable("address") String address) {
        List<Person> persons = personDao.findAll();
        List<Firestation> firestations = firestationDao.findByAddress(address);
        List<Person> personCovered = new ArrayList<>();
         for (Firestation firestation :  firestations) {
            for (Person person : persons) {
                if (person.getAddress().equals(firestation.getAddress())) {
                    person.setStation(firestation.getStation());
                    personCovered.add(person);
                }
            }
        }
        HashMap<List<Person>, List<Person>>  childrenAtAddress =  alertsUtils.countChildAndHouse(personCovered);
        return childrenAtAddress;
    }


    /**
     * This method is reponsible for getting :
     * firstName, lastName, phone
     * children and adults count
     * covered by firestations targeted by station number
     * @param station
     * @return
     */
     @Override
    public List<Object> getPersonCoveredByFirestation(@PathVariable("station") int station)   {
        List<Person> persons = personDao.findAll();
        List<Firestation> firestations = firestationDao.findByStation(station);
        List<Person> personCovered = new ArrayList<>();
        List<Object> personCoveredByFirestation = new ArrayList<>();
         for (Firestation firestation :  firestations) {
            for (Person person : persons) {
                if (person.getAddress().equals(firestation.getAddress())) {
                    person.setStation(firestation.getStation());
                    personCovered.add(person);
                }
            }
        }
        personCoveredByFirestation.add(personCovered);
        List<Object> count = alertsUtils.getPersonCount(personCovered);
        personCoveredByFirestation.add(count);
        return personCoveredByFirestation;
    }

}