package com.safetynet.alerts.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class MedicalTest {

    @Test
    public void setAndGetMedical() {
        Medical medical = Medical.builder()
                .id(1).birthdate("11/1/1970").firstName("Jeanne")
                .lastName("Martin").allergies(List.of("Gluten"))
                .medications(List.of("Doliprane")).build();
        medical.setLastName("Smith");
        medical.setFirstName("John");
        medical.setAllergies(List.of("Glutamine"));
        medical.setMedications(List.of("Ibuprofene"));
        assertEquals(1, medical.getId());
        assertEquals("11/1/1970", medical.getBirthdate());
        assertEquals("John", medical.getFirstName());
        assertEquals("Smith", medical.getLastName());
        assertEquals(List.of("Glutamine"), medical.getAllergies());
        assertEquals(List.of("Ibuprofene"), medical.getMedications());
    }
}
