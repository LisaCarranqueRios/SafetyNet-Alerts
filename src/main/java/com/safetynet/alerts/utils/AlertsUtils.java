package com.safetynet.alerts.utils;

import com.safetynet.alerts.model.Person;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class AlertsUtils {

    public static Integer getAge(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
        LocalDate localDate = LocalDate.parse(birthdate, formatter);
        Period period = Period.between(localDate, LocalDate.now());
        int age = period.getYears();
        return age;
    }

    public List<Object> getPersonCount(List<Person> personCoveredByFirestation) {
        int i = 0;
        int j = 0;
        List<Object> count = new ArrayList<>();
        for (Person person : personCoveredByFirestation) {
            String birthday = person.getMedical().getBirthdate();
            if (Integer.valueOf(AlertsUtils.getAge(birthday)) < 18) {
                i += 1;
            } else {
                j += 1;
            }
        }
        List<Object> count1 = new ArrayList<>();
        count1.add("adultCount");
        count1.add(j);
        List<Object> count2 = new ArrayList<>();
        count1.add("childCount");
        count1.add(i);
        count.add(count1);
        count.add(count2);
        return count;
    }

    public HashMap<List<Person>, List<Person>> countChildAndHouse(List<Person> personCoveredByFirestation) {
        HashMap<List<Person>, List<Person>> countChildAndHouse = new HashMap<>();
        List<Person> childrenAndHouse = new ArrayList<>();
        List<Person> houseMembers = new ArrayList<>();
        for (Person person : personCoveredByFirestation) {
            if (AlertsUtils.getAge(person.getMedical().getBirthdate()) <= 18) {
                person.setAge(AlertsUtils.getAge(person.getMedical().getBirthdate()));
                childrenAndHouse.add(person);
            } else {
                person.setAge(AlertsUtils.getAge(person.getMedical().getBirthdate()));
                houseMembers.add(person);
            }
            countChildAndHouse.put(childrenAndHouse, houseMembers);
        }
        return countChildAndHouse;
    }

}
