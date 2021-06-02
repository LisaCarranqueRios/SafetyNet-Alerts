package com.safetynet.alerts.utils;

import com.safetynet.alerts.mapper.PersonMapper;
import com.safetynet.alerts.model.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains methods used to map a Person instance in a PersonMapper instance
 */
public class MapperUtils {

    /**
     * This method is used to map a Person instance in a PersonMapper instance with the following information :
     * - firstName
     * - lastName
     * - phone
     * @param persons
     * @return
     */
    public static List<PersonMapper> getPersonCoveredByFirestationPhoneNumberMapper(List<Person> persons) {
        List<PersonMapper> personMappers = new ArrayList<>();
        for (Person person : persons) {
            PersonMapper personMapper = PersonMapper.builder()
                    .firstName(person.getFirstName())
                    .lastName(person.getLastName())
                    .phone(person.getPhone())
                    .build();
            personMappers.add(personMapper);
        }
        return personMappers;
    }

    /**
     * This method is responsible for mapping a Person instance in a PersonMapper instance with the following information :
     * - firstName
     * - lastName
     * - phone
     * - age
     * - medical data
     * - address
     * @param persons
     * @return
     */
    public static List<PersonMapper> getPersonsInformationAndMedicalDataMapper(List<Person> persons) {
        List<PersonMapper> personMappers = new ArrayList<>();
        for (Person person : persons) {
            PersonMapper personMapper = PersonMapper.builder()
                    .firstName(person.getFirstName())
                    .lastName(person.getLastName())
                    .phone(person.getPhone())
                    .age(person.getAge())
                    .medical(person.getMedical())
                    .address(person.getAddress()).build();
            personMappers.add(personMapper);
        }
        return personMappers;
    }

    /**
     * This method is used to map a Person instance in a PersonMapper instance with the following information :
     * - firstName
     * - lastName
     * - email
     * - medical data
     * @param persons
     * @return
     */
    public static List<PersonMapper> getOnePersonInformationByNamesMapper(List<Person> persons) {
        List<PersonMapper> personMappers = new ArrayList<>();
        for (Person person : persons) {
            PersonMapper personMapper = PersonMapper.builder()
                    .firstName(person.getFirstName())
                    .lastName(person.getLastName())
                    .email(person.getEmail())
                    .medical(person.getMedical()).build();
            personMappers.add(personMapper);
        }
        return personMappers;
    }

    /**
     * This method is used to map a Person instance in a PersonMapper instance with the following information :
     * - lastName
     * - firstName
     * - phone
     * - address
     * - station
     * @param persons
     * @return
     */
    public static List getPersonCoveredByFirestationMapper(List<Person> persons) {
        List<PersonMapper> personCoveredMapper = new ArrayList<>();
        for (Person person : persons)  {
            PersonMapper personMapper = PersonMapper.builder()
                    .lastName(person.getLastName())
                    .firstName(person.getFirstName())
                    .phone(person.getPhone())
                    .address(person.getAddress())
                    .station(person.getStation())
                    .build();
            personCoveredMapper.add(personMapper);
        }
        return personCoveredMapper;
    }

    }