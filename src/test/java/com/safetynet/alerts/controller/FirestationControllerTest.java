package com.safetynet.alerts.controller;

import com.safetynet.alerts.service.IFirestationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class FirestationControllerTest {

    @InjectMocks
    FirestationController firestationController;

    @Mock
    IFirestationService firestationService;

    @Test
    public void listFirestation() {
        firestationController.listFirestation();
        verify(firestationService, times(1)).listFirestation();
    }

    @Test
    public void displayFirestation() {
        firestationController.displayFirestation(1);
        verify(firestationService, times(1)).displayFirestation(1);
    }

    @Test
    public void addFirestation() {
        firestationController.addFirestation(any());
        verify(firestationService, times(1)).addFirestation(any());
    }

    @Test
    public void addFirestations() {
        firestationController.addFirestations(any());
        verify(firestationService, times(1)).addFirestations(any());
    }

    @Test
    public void deleleFirestation() {
        firestationController.deleteFirestation(1);
        verify(firestationService, times(1)).deleteFirestation(1);
    }

    @Test
    public void updateFirestation() {
        firestationController.updateFirestation(any());
        verify(firestationService, times(1)).updateFirestation(any());
    }
}
