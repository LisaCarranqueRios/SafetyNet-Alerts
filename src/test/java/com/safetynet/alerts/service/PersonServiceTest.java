package com.safetynet.alerts.service;

import com.safetynet.alerts.dao.FirestationDao;
import com.safetynet.alerts.dao.MedicalDao;
import com.safetynet.alerts.dao.PersonDao;
import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    FirestationDao firestationDao;

    @Mock
    PersonDao personDao;

    @Mock
    MedicalDao medicalDao;

    @InjectMocks
    PersonService personService;


    @Test
    public void listPerson() {
        personService.listPerson();
        verify(personDao, times(1)).findAll();
    }

    @Test
    public void displayPerson() {
        personService.displayPerson(1);
        verify(personDao, times(1)).findById(anyInt());
    }

    @Test
    public void addPerson() {
        personService.addPerson(Person.builder().id(1).build());
        verify(personDao, times(1)).save(any());
    }

    @Test
    public void addPersons() {
        List<Person> personList = List.of(new Person());
        personService.addPersons(personList);
        verify(medicalDao, times(1)).findAll();
        verify(firestationDao, times(1)).findAll();
        verify(personDao, times(1)).save(any());
    }

    @Test
    public void deletePerson() {
        personService.deletePerson(1);
        verify(personDao, times(1)).deleteById(anyInt());
    }

    @Test
    public void updatePerson() {
        personService.updatePerson(new Person());
        verify(personDao, times(1)).save(any());
    }

}
