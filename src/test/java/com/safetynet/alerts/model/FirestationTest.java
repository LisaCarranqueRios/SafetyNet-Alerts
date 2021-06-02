package com.safetynet.alerts.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class FirestationTest {

    @Test
    public void setAndGetFirestation() {
        Firestation firestation = Firestation.builder()
                .station(1).address("101 street").id(1).build();
        assertEquals(1, firestation.getStation());
        assertEquals("101 street", firestation.getAddress());
        assertEquals(1, firestation.getId());
        firestation.setAddress("102 street");
        assertEquals("102 street", firestation.getAddress());
    }
}
