package com.safetynet.alerts.service;

import com.safetynet.alerts.dao.FirestationDao;
import com.safetynet.alerts.dao.MedicalDao;
import com.safetynet.alerts.dao.PersonDao;
import com.safetynet.alerts.exception.FirestationsException;
import com.safetynet.alerts.exception.PersonsException;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Medical;
import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

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
        when(personDao.findAll()).thenReturn(List.of(Person.builder().firstName("Jeanne").build()));
        personService.listPerson();
        verify(personDao, times(1)).findAll();
    }

    @Test
    public void listPersonThrowsException() {
        Exception exception = assertThrows(PersonsException.class, () -> personService.listPerson());
        assertEquals("Une erreur est survenue durant la récupération des personnes", exception.getMessage());
    }

    @Test
    public void displayPerson() {
        when(personDao.findById(1)).thenReturn(Person.builder().id(1).build());
        Person person = personService.displayPerson(1);
        verify(personDao, times(1)).findById(anyInt());
        assertEquals(1, person.getId());
    }

    @Test
    public void displayPersonThrowsException() {
        Exception exception = assertThrows(PersonsException.class, () -> personService.displayPerson(1));
        assertEquals("Erreur lors de la récupération de la personne", exception.getMessage());
    }

    @Test
    public void addPerson() {
        personService.addPerson(Person.builder().id(1).build());
        verify(personDao, times(1)).save(any());
    }

    @Test
    public void addPersonNoContent() {
        Person person = Person.builder().firstName("Jeanne").build();
        ResponseEntity responseEntityOutput = personService.addPerson(person);
        assertEquals(ResponseEntity.noContent().build(), responseEntityOutput);
    }

    @Test
    public void addPersons() {
        List<Person> persons = List.of(Person.builder().firstName("Jeanne").lastName("Dupont").address("Address").build());
        List<Person> personOutput = List.of(Person.builder().id(1).firstName("Jeanne").build());
        when( medicalDao.findAll()).thenReturn(List.of(Medical.builder().firstName("Jeanne").lastName("Dupont").id(1).build()));
        when( firestationDao.findAll()).thenReturn(List.of(Firestation.builder().id(1).address("Address").build()));
        when(personDao.save(persons.get(0))).thenReturn(personOutput.get(0));
        personService.addPersons(persons);
        verify(personDao, times(1)).save(any());
    }

    @Test
    public void addPersonsThrowsException() {
        List<Person> persons = List.of(Person.builder().build());
        Exception exception = assertThrows(PersonsException.class, () -> personService.addPersons(persons));
        assertEquals("Une erreur est survenue durant l'ajout des personnes", exception.getMessage());
    }

    @Test
    public void deletePerson() {
        personService.deletePerson(1);
        verify(personDao, times(1)).deleteById(anyInt());
    }

    @Test
    public void deletePersonThrowsException() {
        personService.deletePerson(1);
        verify(personDao, times(1)).deleteById(anyInt());
        when(personDao.findById(1)).thenReturn(Person.builder().id(1).build());
        Exception exception = assertThrows(PersonsException.class, () -> personService.deletePerson(1));
        assertEquals("Une erreur est survenue durant le retrait de la personne", exception.getMessage());
    }

    @Test
    public void updatePerson() {
        Person personToUpdate = Person.builder().id(1).build();
        Person savedPerson = Person.builder().id(1).build();
        when(personDao.save(personToUpdate)).thenReturn(savedPerson);
        personService.updatePerson(personToUpdate);
        verify(personDao, times(1)).save(any());
    }

    @Test
    public void updatePersonThrowsException() {
        Exception exception = assertThrows(PersonsException.class, () -> personService.updatePerson(Person.builder().build()));
        assertEquals("Une erreur est survenue durant la mise à jour de la personne", exception.getMessage());
    }

}
