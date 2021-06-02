package com.safetynet.alerts.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PersonTest {

    @Test
    public void setAndGetPerson() {
        Person person = Person.builder().id(1).lastName("Smith")
                .firstName("John").phone("023456")
                .address("Culver St").city("Culver")
                .email("john@mail.com")
                .zip("2345").build();
        person.setId(2);
        person.setAddress("101 Culver St");
        person.setPhone("0123456");
        person.setEmail("smith@mail.com");
        person.setCity("Coulver");
        person.setZip("23456");
        assertEquals(2, person.getId());
        assertEquals("smith@mail.com", person.getEmail());
        assertEquals("Coulver", person.getCity());
        assertEquals("23456", person.getZip());
        assertEquals("0123456", person.getPhone());
        assertEquals("101 Culver St", person.getAddress());
    }

}
