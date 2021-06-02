package com.safetynet.alerts.service;

import com.safetynet.alerts.dao.FirestationDao;
import com.safetynet.alerts.dao.MedicalDao;
import com.safetynet.alerts.dao.PersonDao;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Medical;
import com.safetynet.alerts.model.Person;
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
public class PersonService  implements IPersonService {

    @Autowired
    PersonDao personDao;

    @Autowired
    MedicalDao medicalDao;

    @Autowired
    FirestationDao firestationDao;

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

}
