package com.safetynet.alerts.service;


import com.safetynet.alerts.dao.FirestationDao;
import com.safetynet.alerts.dao.PersonDao;
import com.safetynet.alerts.exception.AlertsException;
import com.safetynet.alerts.mapper.ChildMapper;
import com.safetynet.alerts.mapper.CountMapper;
import com.safetynet.alerts.mapper.PersonMapper;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Medical;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.utils.AlertsUtils;
import com.safetynet.alerts.utils.MapperUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.xmlunit.util.Mapper;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlertsServiceTest {

    @Mock
    PersonDao personDao;

    @Mock
    AlertsUtils alertsUtils;

    @Mock
    MapperUtils mapperUtils;

    @InjectMocks
    AlertsService alertsService;

    @Test
    public void getPersonEmail() {
        when(personDao.getPersonEmail(anyString())).thenReturn(Collections.singletonList("email"));
        List<String> personEmails = alertsService.getPersonEmail("");
        assertEquals("email", personEmails.get(0));
    }

    @Test
    public void getPersonEmailThrowsException() {
        Exception exception = assertThrows(AlertsException.class, () -> alertsService.getPersonEmail(""));
        assertEquals("Erreur lors de la récupération des emails.", exception.getMessage());
    }

    @Test
    public void getPersonAtAddress() {
        Medical medical = Medical.builder().birthdate("03/11/1990").medications(List.of("Doliprane")).allergies(List.of("Gluten")).build();
        Person person = Person.builder().firstName("Jean").medical(medical).build();
        when(personDao.findByStation(1)).thenReturn(List.of(person));
        List<Person> personList = alertsService.getPersonAtAddress(1);
        verify(personDao, times(1)).findByStation(1);
        assertEquals("Jean",personList.get(0).getFirstName());
    }

    @Test
    public void getPersonAtAddressThrowsException() {
        Exception exception = assertThrows(AlertsException.class, () -> alertsService.getPersonAtAddress(1));
        assertEquals(exception.getMessage(), "Erreur lors de la récupération des résidents à l'adresse couverte par la station : 1");
    }

    @Test
    public void getPersonsAtStations() {
        Medical medical = Medical.builder().birthdate("03/11/1990").medications(List.of("Doliprane")).allergies(List.of("Gluten")).build();
        Person person = Person.builder().firstName("Jean").medical(medical).build();
        when(personDao.findByStation(1)).thenReturn(List.of(person));
        Medical medical2 = Medical.builder().birthdate("03/11/1990").medications(List.of("Doliprane")).allergies(List.of("Gluten")).build();
        Person person2 = Person.builder().firstName("Jeanne").medical(medical2).build();
        when(personDao.findByStation(2)).thenReturn(List.of(person2));
        List<Person> personsAtStations = alertsService.getPersonsAtStations(List.of(Integer.valueOf(1), Integer.valueOf(2)));
        verify(personDao, times(2)).findByStation(anyInt());
        assertEquals(personsAtStations.get(0).getFirstName(), "Jean");
        assertEquals(personsAtStations.get(1).getFirstName(), "Jeanne");
    }

    @Test
    public void getPersonsAtStationsThrowsException() {
        Exception exception = assertThrows(AlertsException.class, () -> alertsService.getPersonsAtStations(List.of(Integer.valueOf(1), Integer.valueOf(2))));
        assertEquals(exception.getMessage(), "Erreur lors de la récupération des résidents à l'adresse couverte par la station : 1");
    }

    @Test
    public void getPersonAtAddressWithFirestationCoverage() {
        Firestation firestation = Firestation.builder().id(1).station(1).build();
        Medical medical = Medical.builder().birthdate("03/11/1990").build();
        Person person1 = Person.builder().firstName("Jean").lastName("Dupont").firestation(firestation).medical(medical).build();
        when(personDao.findByAddress("Address")).thenReturn(List.of(person1));
        List<Person> personList = alertsService.getPersonAtAddressWithFirestationCoverage("Address");
        verify(personDao, times(1)).findByAddress("Address");
        assertEquals(personList.get(0).getFirstName(), "Jean");
    }

    @Test
    public void getPersonAtAddressWithFirestationCoverageThrowsException() {
        Exception exception = assertThrows(AlertsException.class, () -> alertsService.getPersonAtAddressWithFirestationCoverage("Address"));
        assertEquals(exception.getMessage(), "Erreur lors de la récupération des résidents à l'adresse : Address");
    }


    @Test
    public void getPersonCoveredByFirestationPhoneNumber() {
        Person person = Person.builder().phone("012345").build();
        when(personDao.findByStation(1)).thenReturn(List.of(person));
        List<Person> personList = alertsService.getPersonCoveredByFirestationPhoneNumber(1);
        verify(personDao, times(1)).findByStation(1);
        assertEquals("012345", personList.get(0).getPhone());
    }

    @Test
    public void getPersonCoveredByFirestationPhoneNumberThrowsException() {
        Exception exception = assertThrows(AlertsException.class, () -> alertsService.getPersonCoveredByFirestationPhoneNumber(1));
        assertEquals(exception.getMessage(), "Erreur lors de la récupération des numéros de téléphone des résidents couverts par la station : 1");
    }

    @Test
    public void getChildrenAtAddress() {
        Firestation firestation = Firestation.builder().station(1).build();
        Medical medical = Medical.builder().birthdate("03/11/1990").build();
        Person person1 = Person.builder().medical(medical).firestation(firestation).firstName("Jean").build();
        Medical medical2 = Medical.builder().birthdate("03/11/2000").build();
        Person person2 = Person.builder().medical(medical2).firestation(firestation).firstName("Jeanne").build();
        when(personDao.findByAddress("Address")).thenReturn(List.of(person1, person2));
        ChildMapper personList = alertsService.getChildrenAtAddress("Address");
        verify(personDao, times(1)).findByAddress("Address");
    }

    @Test
    public void getChildrenAtAddressThrowsException() {
        Exception exception = assertThrows(AlertsException.class, () -> alertsService.getChildrenAtAddress("Address"));
        assertEquals(exception.getMessage(), "Erreur lors de la récupération des enfants résidents à l'adresse : Address");
    }

    @Test
    public void getPersonCoveredByFirestation() {
        CountMapper count = CountMapper.builder().adultCount(1).childCount(1).build();
        List<Person> persons = List.of(Person.builder().firstName("Jeanne").station(1).build());
        when(personDao.findByStation(anyInt())).thenReturn(persons);
        when(alertsUtils.getPersonCount(anyList())).thenReturn(count);
        alertsService.getPersonCoveredByFirestation(1);
        verify(personDao, times(1)).findByStation(anyInt());
    }

    @Test
    public void getPersonCoveredByFirestationThrowsException() {
        Exception exception = assertThrows(AlertsException.class, () -> alertsService.getPersonCoveredByFirestation(1));
        assertEquals(exception.getMessage(), "Erreur lors de la récupération des personnes couvertes par la caserne : 1");
    }

    @Test
    public void getPersonInformationByNames() {
        Firestation firestation = Firestation.builder().station(1).build();
        Medical medical1 = Medical.builder().birthdate(new SimpleDateFormat("MM/d/yyyy").format(new Date())).build();
        Person person1 = Person.builder().firstName("Jean").lastName("Paul")
                .email("jeanpaul@mail.com").address("101 Street").phone("012346").medical(medical1).firestation(firestation).build();
        List<Person> persons = List.of(person1);
        when(personDao.findByFirstNameAndLastName("Jean", "Paul")).thenReturn(persons);
        List<Person> personInformationByNames =  alertsService.getPersonInformationByNames("Jean", "Paul");
        assertEquals(Integer.valueOf(0), personInformationByNames.get(0).getAge());
    }

    @Test
    public void getPersonInformationByNamesThrowsException() {
        Exception exception = assertThrows(AlertsException.class, () -> alertsService.getPersonInformationByNames("", ""));
        assertEquals("Erreur lors de la récupération de la personne.", exception.getMessage());
    }

    @Test
    public void getPersonAtAddressReturnAge() {
        Firestation firestation = Firestation.builder().station(1).build();
        Medical medical1 = Medical.builder().birthdate(new SimpleDateFormat("MM/d/yyyy").format(new Date())).build();
        Medical medical2 = Medical.builder().birthdate("11/1/1970").build();
        Person person1 = Person.builder().firstName("Jean").lastName("Paul")
                .email("jeanpaul@mail.com").address("101 Street").phone("012346").medical(medical1).firestation(firestation).build();
        Person person2 = Person.builder().firstName("Jacqueline").lastName("Dupont")
                .email("jacquelinedupont@mail.com").address("101 Street").phone("012345").medical(medical2).firestation(firestation).build();
        List<Person> persons = List.of(person1, person2);
        when(personDao.findByStation(1)).thenReturn(persons);
        List<Person> personsAtAddress = alertsService.getPersonAtAddress(Integer.valueOf(1));
        assertEquals(Integer.valueOf(0), personsAtAddress.get(0).getAge());
        assertNotNull(personsAtAddress.get(1).getAge());
    }

    @Test
    public void getPersonAtAddressWithFirestationCoverageReturnStationAndAge() {
        Firestation firestation = Firestation.builder().station(1).build();
        Medical medical1 = Medical.builder().birthdate(new SimpleDateFormat("MM/d/yyyy").format(new Date())).build();
        Medical medical2 = Medical.builder().birthdate("11/1/1970").build();
        Person person1 = Person.builder().firstName("Jean").lastName("Paul")
                .email("jeanpaul@mail.com").address("101 Street").phone("012346").medical(medical1).firestation(firestation).build();
        Person person2 = Person.builder().firstName("Jacqueline").lastName("Dupont")
                .email("jacquelinedupont@mail.com").address("101 Street").phone("012345").medical(medical2).firestation(firestation).build();
        List<Person> persons = List.of(person1, person2);
        when(personDao.findByAddress("101 Street")).thenReturn(persons);
        List<Person> personAtAddressWithFirestationCoverage = alertsService.getPersonAtAddressWithFirestationCoverage("101 Street");
        assertEquals(2, personAtAddressWithFirestationCoverage.size());
        assertEquals(Integer.valueOf(0), personAtAddressWithFirestationCoverage.get(0).getAge());
        assertNotNull(personAtAddressWithFirestationCoverage.get(1).getAge());
        assertEquals(Integer.valueOf(1), personAtAddressWithFirestationCoverage.get(0).getStation());
        assertEquals(Integer.valueOf(1), personAtAddressWithFirestationCoverage.get(1).getStation());
    }


    @Test
    public void getChildrenAtAddressReturnChildren() {
        Firestation firestation = Firestation.builder().address("101 Street").station(1).build();
        Medical medical1 = Medical.builder().birthdate(new SimpleDateFormat("MM/d/yyyy").format(new Date())).build();
        Medical medical2 = Medical.builder().birthdate("11/1/1970").build();
        Person person1 = Person.builder().firstName("Jean")
                .lastName("Paul").email("jeanpaul@mail.com")
                .address("101 Street").age(40).firestation(firestation).medical(medical1).build();
        Person person2 = Person.builder().firstName("Jeanne")
                .lastName("Dupont").email("jeannedupont@mail.com")
                .address("101 Street").age(5).firestation(firestation).medical(medical2).build();
        List<Person> persons = List.of(person1, person2);

        PersonMapper personMapper1 = PersonMapper.builder()
                .firstName(person1.getFirstName())
                .lastName(person1.getLastName())
                .age(AlertsUtils.getAge(person1.getMedical().getBirthdate()))
                .build();
        PersonMapper personMapper2 = PersonMapper.builder()
                .firstName(person2.getFirstName())
                .lastName(person2.getLastName())
                .age(AlertsUtils.getAge(person2.getMedical().getBirthdate()))
                .build();

        when(personDao.findByAddress("101 Street")).thenReturn(persons);
        when(alertsUtils.listChildAndHouse(persons)).thenReturn(ChildMapper.builder().children(List.of(personMapper1)).houseMembers(List.of(personMapper2)).build());
        ChildMapper childrenAtAddress =  alertsService.getChildrenAtAddress("101 Street");
        assertEquals(1, childrenAtAddress.getChildren().size());
        assertEquals(1, childrenAtAddress.getHouseMembers().size());
     }

    @Test
    public void getPersonCoveredByFirestationReturnPersonData() {
        Person person1 = Person.builder().firstName("Jean").lastName("Paul")
                .email("jeanpaul@mail.com").address("101 Street").phone("012346").station(1).build();

        Person person2 = Person.builder().firstName("Jacqueline").lastName("Dupont")
                .email("jacquelinedupont@mail.com").address("101 Street").phone("012345").station(1).build();

        List<Person> persons = List.of(person1, person2);

        PersonMapper personMapper1 = PersonMapper.builder()
                .firstName(person1.getFirstName())
                .lastName(person1.getLastName())
                 .address(person1.getAddress())
                .phone(person1.getPhone())
                .station(person1.getStation()).build();

        PersonMapper personMapper2 = PersonMapper.builder()
                .firstName(person2.getFirstName())
                .lastName(person2.getLastName())
                 .address(person2.getAddress())
                .phone(person2.getPhone())
                .station(person2.getStation()).build();

        List<Object> personMappers = List.of(personMapper1, personMapper2);

        when(personDao.findByStation(1)).thenReturn(persons);
        when(alertsUtils.getPersonCount(any())).thenReturn(CountMapper.builder().childCount(1).adultCount(1).build());
        CountMapper personCoveredByFirestation =  alertsService.getPersonCoveredByFirestation(1);
        assertEquals(1, personCoveredByFirestation.getAdultCount());
        assertEquals(1, personCoveredByFirestation.getChildCount());
        assertEquals(2, personCoveredByFirestation.getPersonsAtFirestation().size());
    }

}
