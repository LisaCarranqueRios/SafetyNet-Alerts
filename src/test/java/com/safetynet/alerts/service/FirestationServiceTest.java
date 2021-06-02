package com.safetynet.alerts.service;

import com.safetynet.alerts.dao.FirestationDao;
import com.safetynet.alerts.model.Firestation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class FirestationServiceTest {

    @Mock
    FirestationDao firestationDao;

    @InjectMocks
    FirestationService firestationService;

    @Test
    public void listFirestation() {
        firestationService.listFirestation();
        verify(firestationDao, times(1)).findAll();
    }

    @Test
    public void displayFirestation() {
        firestationService.displayFirestation(1);
        verify(firestationDao, times(1)).findById(anyInt());
    }

    @Test
    public void addFirestation() {
        firestationService.addFirestation(Firestation.builder().id(1).build());
        verify(firestationDao, times(1)).save(any());
    }

    @Test
    public void addFirestations() {
        List<Firestation> firestations = List.of(Firestation.builder().build());
        firestationService.addFirestations(firestations);
        verify(firestationDao, times(1)).save(any());
    }

    @Test
    public void deleteFirestation() {
        firestationService.deleteFirestation(1);
        verify(firestationDao, times(1)).deleteById(anyInt());
    }

    @Test
    public void updateFirestation() {
        firestationService.updateFirestation(Firestation.builder().build());
        verify(firestationDao, times(1)).save(any());
    }


}
