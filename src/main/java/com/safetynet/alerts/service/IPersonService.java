package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IPersonService {

    /**
     * Manage persons
     *
     * @return
     */
    public List<Person> listPerson();

    public Person displayPerson(int id);

    public ResponseEntity<Object> addPerson(Person person);

    public void addPersons(List<Person> persons);

    public void deletePerson(int id);

    public void updatePerson(Person person);

}
