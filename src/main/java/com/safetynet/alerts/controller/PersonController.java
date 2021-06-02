package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.IPersonService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
public class PersonController {

    @Autowired
    IPersonService personService;

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
        return personService.listPerson();
    }

    /**
     * This method is responsible for selecting a person by id
     *
     * @param id the id of the targeted person
     * @return the person targeted by id
     */
    @GetMapping(value = "/persons/{id}")
    public Person displayPerson(@PathVariable int id) {
        return personService.displayPerson(id);
    }

    /**
     * This method is responsible for adding a person in the database
     *
     * @param person the person to add in the database
     * @return a 201 code with saved object url
     */
    @PostMapping(value = "/person")
    public ResponseEntity<Object> addPerson(@RequestBody Person person) {
        return personService.addPerson(person);
    }

    /**
     * This method is responsible for adding a list of persons in the database
     *
     * @param persons the list of persons to add in the database
     */
    @PostMapping(value = "/persons")
    public void addPersons(@RequestBody List<Person> persons) {
        personService.addPersons(persons);
    }

    /**
     * This method is responsible for removing a person from database
     *
     * @param id the id of the selected person to remove
     */
    @DeleteMapping(value = "/persons/{id}")
    public void deletePerson(@PathVariable int id) {
        personService.deletePerson(id);
    }

    /**
     * This method is responsible for updating person data in the database
     *
     * @param person the person to update in the database
     */
    @PutMapping(value = "/persons")
    public void updatePerson(@RequestBody Person person) {
        personService.updatePerson(person);
    }

}
