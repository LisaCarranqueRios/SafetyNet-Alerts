package com.safetynet.alerts.utils;

import com.safetynet.alerts.mapper.ChildMapper;
import com.safetynet.alerts.mapper.CountMapper;
import com.safetynet.alerts.mapper.PersonMapper;
import com.safetynet.alerts.model.Person;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Component
public class AlertsUtils {


    /**
     * This method is used to calculate the age of a person from it's birthdate
     * @param birthDate The birthdate used to calculate the age, formatted with the pattern : "MM/d/yyyy"
     * @return an integer which represents the age of the person
     */
    public static Integer getAge(String birthDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
        LocalDate localDate = LocalDate.parse(birthDate, formatter);
        Period period = Period.between(localDate, LocalDate.now());
        int age = period.getYears();
        log.debug("Calculate age from medical data birthdate "+ birthDate);
        return age;
    }

    /**
     * This method is used to calculate the number of children and the number of adults in a list of persons
     * @param personCoveredByFirestation the selected list of person
     * @return an object with the count of adults and the count of children in this list
     */
    public CountMapper getPersonCount(List<Person> personCoveredByFirestation) {
        int i = 0;
        int j = 0;
        for (Person person : personCoveredByFirestation) {
            if (AlertsUtils.getAge(person.getMedical().getBirthdate()) < 18) {
                i += 1;
            } else {
                j += 1;
            }
        }
        log.debug("Calculate the number of children and the number of adults in the list of persons");
        return CountMapper.builder().childCount(i).adultCount(j).build();
    }

    /**
     * This method is used to list all the children and their house members in a selected house
     * @param personCoveredByFirestation the selected list of person in a house
     * @return an object with the list of children and the list of the associated house members
     */
    public ChildMapper listChildAndHouse(List<Person> personCoveredByFirestation) {
        List<PersonMapper> children = new ArrayList<>();
        List<PersonMapper> houseMembers = new ArrayList<>();
        for (Person person : personCoveredByFirestation) {
            PersonMapper personMapper = PersonMapper.builder()
                    .firstName(person.getFirstName())
                    .lastName(person.getLastName())
                    .age(AlertsUtils.getAge(person.getMedical().getBirthdate()))
                    .build();
            if (AlertsUtils.getAge(person.getMedical().getBirthdate()) <= 18) {
                children.add(personMapper);
            } else {
                houseMembers.add(personMapper);
            }
        }
        ChildMapper childAndHouseMapper = ChildMapper.builder().children(children).houseMembers(houseMembers).build();
        log.debug("List all the children and their house members in the selected house");
        return childAndHouseMapper;
    }

}
