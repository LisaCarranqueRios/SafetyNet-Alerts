package com.safetynet.alerts.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PersonTest {

    @Test
    public void setAndGetPerson() {
        Person person = Person.builder().build();
        person.setId(1);
        person.setEmail("dupont@mail.com");
        person.setCity("Culver");
        person.setZip("2345");
        assertEquals(1, person.getId());
        assertEquals("dupont@mail.com", person.getEmail());
        assertEquals("Culver", person.getCity());
        assertEquals("2345", person.getZip());
    }

}
