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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Log4j2
@Service
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
    @Transactional(propagation = Propagation.REQUIRES_NEW,  readOnly = true)
    @Override
    public List<Person> listPerson() {
        return personDao.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,  readOnly = true)
    @Override
    public Person displayPerson(@PathVariable int id) {
        return personDao.findById(id);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
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

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
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

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void deletePerson(@PathVariable int id) {
        personDao.deleteById(id);
        log.info("Person removed from database");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void updatePerson(@RequestBody Person person) {
        personDao.save(person);
        log.info("Person updated into database");
    }

}
