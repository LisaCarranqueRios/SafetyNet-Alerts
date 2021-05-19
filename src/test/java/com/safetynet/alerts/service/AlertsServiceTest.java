package com.safetynet.alerts.service;


import com.safetynet.alerts.dao.FirestationDao;
import com.safetynet.alerts.dao.MedicalDao;
import com.safetynet.alerts.dao.PersonDao;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Medical;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.utils.AlertsUtils;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlertsServiceTest {


    @Mock
    FirestationDao firestationDao;

    @Mock
    PersonDao personDao;

    @Mock
    MedicalDao medicalDao;

    @Mock
    AlertsUtils alertsUtils;

    @InjectMocks
    AlertsService alertsService;


    @Test
    public void listPerson() {
        alertsService.listPerson();
        verify(personDao, times(1)).findAll();
    }

    @Test
    public void displayPerson() {
        alertsService.displayPerson(1);
        verify(personDao, times(1)).findById(anyInt());
    }

    @Test
    public void addPerson() {
        alertsService.addPerson(new Person());
        verify(personDao, times(1)).save(any());
    }

    @Test
    public void addPersons() {
        List<Person> personList = List.of(new Person());
        alertsService.addPersons(personList);
        verify(medicalDao, times(1)).findAll();
        verify(firestationDao, times(1)).findAll();
        verify(personDao, times(1)).save(any());
    }

    @Test
    public void deletePerson() {
        alertsService.delelePerson(1);
        verify(personDao, times(1)).deleteById(anyInt());
    }

    @Test
    public void updatePerson() {
        alertsService.updatePerson(new Person());
        verify(personDao, times(1)).save(any());
    }

    @Test
    public void listFirestation() {
        alertsService.listFirestation();
        verify(firestationDao, times(1)).findAll();
    }

    @Test
    public void displayFirestation() {
        alertsService.displayFirestation(1);
        verify(firestationDao, times(1)).findById(anyInt());
    }

    @Test
    public void addFirestation() {
        alertsService.addFirestation(new Firestation());
        verify(firestationDao, times(1)).save(any());
    }

    @Test
    public void addFirestations() {
        List<Firestation> firestations = List.of(new Firestation());
        alertsService.addFirestations(firestations);
        verify(firestationDao, times(1)).save(any());
    }

    @Test
    public void deleteFirestation() {
        alertsService.deleleFirestation(1);
        verify(firestationDao, times(1)).deleteById(anyInt());
    }

    @Test
    public void updateFirestation() {
        alertsService.updateFirestation(new Firestation());
        verify(firestationDao, times(1)).save(any());
    }

    @Test
    public void listMedical() {
        alertsService.listMedical();
        verify(medicalDao, times(1)).findAll();
    }
    @Test
    public void displayMedical() {
        alertsService.displayMedical(1);
        verify(medicalDao, times(1)).findById(anyInt());
    }
    @Test
    public void addMedical() {
        alertsService.addMedical(new Medical());
        verify(medicalDao, times(1)).save(any());
    }
    @Test
    public void addMedicals() {
        Medical medical = new Medical();
        Medical medical2 = new Medical();
        alertsService.addMedicals(List.of(medical, medical2));
        verify(medicalDao, times(2)).save(any());
    }
    @Test
    public void deleteMedical() {
        alertsService.deleleMedical(1);
        verify(medicalDao, times(1)).deleteById(anyInt());
    }
    @Test
    public void updateMedical() {
        alertsService.updateMedical(new Medical());
        verify(medicalDao).save(any());
    }


    @Test
    public void getPersonEmail() {
        alertsService.getPersonEmail("city");
        verify(personDao, times(1)).getPersonEmail(anyString());
    }
    @Test
    public void getPersonInformation() {
        alertsService.getPersonInformation();
        verify(personDao, times(1)).findAll();
    }
    @Test
    public void getOnePersonInformation(){
        alertsService.getOnePersonInformation(new ArrayList<>());
        verify(personDao, times(1)).findAll();
    }
    @Test
    public void getPersonAtAddress() {
        alertsService.getPersonAtAddresse(1, new ArrayList<>());
        verify(personDao, times(1)).findAll();
    }

    @Test
    public void getPersonAtAddressWithFirestationCoverage() {
        alertsService.getPersonAtAddressWithFirestationCoverage(" ");
        verify(personDao, times(1)).findAll();
        verify(firestationDao, times(1)).findByAddress(anyString());
    }
    @Test
    public void getPersonCoveredByFirestationPhoneNumber() {
        alertsService.getPersonCoveredByFirestationPhoneNumber(1);
        verify(personDao, times(1)).findAll();
        verify(firestationDao, times(1)).findByStation(1);
    }
    @Test
    public void getChildrenAtAddress() {
        alertsService.getChildrenAtAddress(" ");
        verify(personDao, times(1)).findAll();
        verify(firestationDao, times(1)).findByAddress(any());
    }
    @Test
    public void getPersonCoveredByFirestation() {
        alertsService.getPersonCoveredByFirestation(1);
        verify(personDao, times(1)).findAll();
        verify(firestationDao, times(1)).findByStation(anyInt());
    }

    @Test
    public void getPersonEmailReturnPersonEmails() {
        Person person1 = new Person();
        person1.setFirstName("Jean");
        person1.setLastName("Paul");
        person1.setEmail("jeanpaul@mail.com");
        Person person2 = new Person();
        person2.setFirstName("Jacqueline");
        person2.setLastName("Dupont");
        person2.setEmail("jacquelinedupont@mail.com");
        List<Person> persons = List.of(person1, person2);

        when(personDao.getPersonEmail("Culver")).thenReturn(persons);
        assertNotNull(alertsService.getPersonEmail("Culver"));
        assertEquals(2, alertsService.getPersonEmail("Culver").size());
    }

    @Test
    public void getChildrenAtAddressReturnChildren() {
        Person person1 = new Person();
        person1.setFirstName("Jean");
        person1.setLastName("Paul");
        person1.setEmail("jeanpaul@mail.com");
        person1.setAddress("101 Street");
        person1.setAge(40);
        Person person2 = new Person();
        person2.setFirstName("Jeanne");
        person2.setLastName("Dupont");
        person2.setEmail("jeannedupont@mail.com");
        person2.setAddress("101 Street");
        person2.setAge(5);
        List<Person> persons = List.of(person1, person2);

        Firestation firestation = new Firestation();
        firestation.setAddress("101 Street");
        firestation.setStation(1);
        List<Firestation> firestations = List.of(firestation);

        HashMap<List<Person>, List<Person>> countChildAndHouse = new HashMap<>();
        countChildAndHouse.put(List.of(person2), List.of(person1));

        when(personDao.findAll()).thenReturn(persons);
        when(firestationDao.findByAddress("101 Street")).thenReturn(firestations);
        when(alertsUtils.countChildAndHouse(any())).thenReturn(countChildAndHouse);

        assertNotNull(alertsService.getChildrenAtAddress("101 Street"));
    }

    @Test
    public void getPersonCoveredByFirestationReturnPersonData() {
        Person person1 = new Person();
        person1.setFirstName("Jean");
        person1.setLastName("Paul");
        person1.setEmail("jeanpaul@mail.com");
        person1.setAddress("101 Street");
        Person person2 = new Person();
        person2.setFirstName("Jacqueline");
        person2.setLastName("Dupont");
        person2.setEmail("jacquelinedupont@mail.com");
        person2.setAddress("101 Street");

        List<Person> persons = List.of(person1, person2);

        Firestation firestation = new Firestation();
        firestation.setAddress("101 Street");
        firestation.setStation(1);
        List<Firestation> firestations = List.of(firestation);
        when(personDao.findAll()).thenReturn(persons);
        when(firestationDao.findByStation(1)).thenReturn(firestations);
        when(alertsUtils.getPersonCount(any())).thenReturn(List.of(2,0));
        assertEquals(2, alertsService.getPersonCoveredByFirestation(1).size());
        assertTrue(alertsService.getPersonCoveredByFirestation(1).get(0).equals(persons));
        assertTrue(alertsService.getPersonCoveredByFirestation(1).get(1).equals(List.of(2,0)));
    }

}
