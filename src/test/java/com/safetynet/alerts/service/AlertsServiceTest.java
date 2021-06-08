package com.safetynet.alerts.service;


import com.safetynet.alerts.dao.PersonDao;
import com.safetynet.alerts.mapper.ChildMapper;
import com.safetynet.alerts.mapper.CountMapper;
import com.safetynet.alerts.mapper.PersonMapper;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Medical;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.utils.AlertsUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.SimpleDateFormat;
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

    @InjectMocks
    AlertsService alertsService;

    @Test
    public void getPersonEmail() {
        alertsService.getPersonEmail("city");
        verify(personDao, times(1)).getPersonEmail("city");
    }

    @Test
    public void getPersonAtAddress() {
        alertsService.getPersonAtAddress(1);
        verify(personDao, times(1)).findByStation(1);
    }

    @Test
    public void getPersonsAtStations() {
        List<Person> personsAtStations = alertsService.getPersonsAtStations(List.of(Integer.valueOf(1), Integer.valueOf(2)));
        verify(personDao, times(2)).findByStation(anyInt());
    }

    @Test
    public void getPersonAtAddressWithFirestationCoverage() {
        alertsService.getPersonAtAddressWithFirestationCoverage(" ");
        verify(personDao, times(1)).findByAddress(" ");
    }
    @Test
    public void getPersonCoveredByFirestationPhoneNumber() {
        alertsService.getPersonCoveredByFirestationPhoneNumber(1);
        verify(personDao, times(1)).findByStation(1);
    }
    @Test
    public void getChildrenAtAddress() {
        alertsService.getChildrenAtAddress(" ");
        verify(personDao, times(1)).findByAddress(" ");
    }

    @Test
    public void getPersonCoveredByFirestation() {
        CountMapper count = CountMapper.builder().adultCount(1).childCount(1).build();
        when(alertsUtils.getPersonCount(anyList())).thenReturn(count);
        alertsService.getPersonCoveredByFirestation(1);
        verify(personDao, times(1)).findByStation(anyInt());
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
