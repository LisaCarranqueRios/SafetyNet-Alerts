package com.safetynet.alerts.utils;

import com.safetynet.alerts.mapper.PersonMapper;
import com.safetynet.alerts.model.Medical;
import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class AlertsUtilsTest {

    @InjectMocks
    AlertsUtils alertsUtils;

    @Test
    public void getAge() {
        String date = new SimpleDateFormat("MM/d/yyyy").format(new Date());
        assertEquals(Integer.valueOf(0), alertsUtils.getAge(date));
    }

/*
    @Test
    public void getPersonCount() {
        Medical medical1 = Medical.builder().birthdate(new SimpleDateFormat("MM/d/yyyy").format(new Date())).build();
        Medical medical2 = Medical.builder().birthdate("11/1/1970").build();
        Person person1 = Person.builder()
                .firstName("Jean")
                .lastName("Dupont")
                .medical(medical1).build();

        Person person2 = Person.builder()
                .firstName("Sophie")
                .lastName("Martin")
                .medical(medical2).build();

        List<Person> persons = List.of(person1, person2);
        List<Object> count = alertsUtils.getPersonCount(persons);
        assertEquals("adultCount", count.get(0));
        assertEquals(Integer.valueOf(1), count.get(1));
        assertEquals("childCount", count.get(2));
        assertEquals(Integer.valueOf(1), count.get(3));
    }
*/

/*    @Test
    public void listChildAndHouse() {
        Medical medical1 = Medical.builder().birthdate(new SimpleDateFormat("MM/d/yyyy").format(new Date())).build();
        Medical medical2 = Medical.builder().birthdate("11/1/1970").build();
        Person person1 = Person.builder()
                .firstName("Jean")
                .lastName("Dupont")
                .medical(medical1).build();

        Person person2 = Person.builder()
                .firstName("Sophie")
                .lastName("Martin")
                .medical(medical2).build();

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

        List<Object> listChildAndHouse = alertsUtils.listChildAndHouse(persons);

        List<Object> output1 =  new ArrayList<>();
        output1.add("children list");
        output1.add(personMapper1);
        List<Object> output2 =  new ArrayList<>();
        output2.add("adult house members");
        output2.add(personMapper2);

        assertEquals(2, listChildAndHouse.size());
    }*/

}
