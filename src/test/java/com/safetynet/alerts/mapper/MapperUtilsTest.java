package com.safetynet.alerts.mapper;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Medical;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.utils.MapperUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class MapperUtilsTest {

    @InjectMocks
    MapperUtils mapperUtils;

    Medical medical1 = Medical.builder().birthdate(new SimpleDateFormat("MM/d/yyyy").format(new Date())).build();
    Medical medical2 = Medical.builder().birthdate("11/1/1970").build();
    List<Person> persons = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        Firestation firestation = Firestation.builder().station(1).build();

        Person person1 = Person.builder().age(0).firstName("Jean").lastName("Paul")
                .email("jeanpaul@mail.com").address("101 Street").phone("012346").medical(medical1).firestation(firestation).build();
        Person person2 = Person.builder().firstName("Jacqueline").lastName("Dupont")
                .email("jacquelinedupont@mail.com").address("101 Street").phone("012345").medical(medical2).firestation(firestation).build();
        persons = List.of(person1, person2);
    }

    @Test
    public void getOnePersonInformationByNamesMapper() {
        List<PersonMapper> personMappers = mapperUtils.getOnePersonInformationByNamesMapper(persons);
        assertEquals("jeanpaul@mail.com", personMappers.get(0).getEmail());
        assertEquals(medical1, personMappers.get(0).getMedical());
        assertEquals("Jean", personMappers.get(0).getFirstName());
        assertEquals("Paul", personMappers.get(0).getLastName());
        assertNull(personMappers.get(0).getAge());
    }

    @Test
    public void getPersonsAtStationsMapper() {
        List<PersonMapper> personMappers = mapperUtils.getPersonsInformationAndMedicalDataMapper(persons);
        assertEquals("101 Street", personMappers.get(0).getAddress());
        assertEquals(medical1, personMappers.get(0).getMedical());
        assertEquals("Jean", personMappers.get(0).getFirstName());
        assertEquals("Paul", personMappers.get(0).getLastName());
        assertEquals("012346", personMappers.get(0).getPhone());
        assertEquals(Integer.valueOf(0), personMappers.get(0).getAge());
        assertNull(personMappers.get(0).getEmail());
    }


    @Test
    public void getPersonCoveredByFirestationPhoneNumberMapper() {
        List<PersonMapper> personMappers = mapperUtils.getPersonCoveredByFirestationPhoneNumberMapper(persons);
        assertEquals("Jean", personMappers.get(0).getFirstName());
        assertEquals("Paul", personMappers.get(0).getLastName());
        assertEquals("012346", personMappers.get(0).getPhone());
        assertNull(personMappers.get(0).getAge());
    }

}
