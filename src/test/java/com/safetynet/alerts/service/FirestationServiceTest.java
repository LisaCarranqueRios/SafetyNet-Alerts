package com.safetynet.alerts.service;

import com.safetynet.alerts.dao.FirestationDao;
import com.safetynet.alerts.exception.FirestationsException;
import com.safetynet.alerts.model.Firestation;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FirestationServiceTest {

    @Mock
    FirestationDao firestationDao;

    @InjectMocks
    FirestationService firestationService;

    @Test
    public void listFirestation() {
        Firestation firestation1 = Firestation.builder().station(1).build();
        Firestation firestation2 = Firestation.builder().station(2).build();
        when(firestationDao.findAll()).thenReturn(List.of(firestation1, firestation2));
        List<Firestation> firestations = firestationService.listFirestation();
        verify(firestationDao, times(1)).findAll();
        assertEquals(1, firestations.get(0).getStation());
        assertEquals(2, firestations.get(1).getStation());
    }

    @Test
    public void listFirestationThrowsException() {
        Exception exception = assertThrows(FirestationsException.class, () -> firestationService.listFirestation());
        assertEquals("Erreur lors de la récupération des casernes", exception.getMessage());
    }

    @Test
    public void displayFirestation() {
        when(firestationDao.findById(1)).thenReturn(Firestation.builder().station(1).build());
        Firestation firestation = firestationService.displayFirestation(1);
        verify(firestationDao, times(1)).findById(anyInt());
        assertEquals(1, firestation.getStation());
    }

    @Test
    public void displayFirestationThrowsException() {
        Exception exception = assertThrows(FirestationsException.class, () -> firestationService.displayFirestation(1));
        assertEquals("Erreur lors de la récupération de la caserne", exception.getMessage());
    }

    @Test
    public void addFirestation() {
        firestationService.addFirestation(Firestation.builder().station(1).build());
        verify(firestationDao, times( 1)).save(any());
    }

    @Test
    public void addFirestationNoContent() {
        Firestation firestation = Firestation.builder().station(1).build();
        ResponseEntity responseEntityOutput = firestationService.addFirestation(firestation);
        assertEquals(ResponseEntity.noContent().build(), responseEntityOutput);
    }

    @Test
    public void addFirestations() {
        List<Firestation> firestations = List.of(Firestation.builder().station(1).build());
        List<Firestation> firestationsOutput = List.of(Firestation.builder().id(1).station(1).build());
        when(firestationDao.save(firestations.get(0))).thenReturn(firestationsOutput.get(0));
        firestationService.addFirestations(firestations);
        verify(firestationDao, times(1)).save(any());
    }

    @Test
    public void addFirestationsThrowsException() {
        List<Firestation> firestations = List.of(Firestation.builder().build());
        Exception exception = assertThrows(FirestationsException.class, () -> firestationService.addFirestations(firestations));
        assertEquals("Une erreur est survenue durant l'ajout des casernes", exception.getMessage());
    }

    @Test
    public void deleteFirestation() {
        firestationService.deleteFirestation(1);
        verify(firestationDao, times(1)).deleteById(anyInt());
    }

    @Test
    public void deleteFirestationThrowsException() {
        firestationService.deleteFirestation(1);
        verify(firestationDao, times(1)).deleteById(anyInt());
        when(firestationDao.findById(1)).thenReturn(Firestation.builder().id(1).build());
        Exception exception = assertThrows(FirestationsException.class, () -> firestationService.deleteFirestation(1));
        assertEquals("Une erreur est survenue durant la suppression de la caserne", exception.getMessage());
    }

    @Test
    public void updateFirestation() {
        Firestation firestationToUpdate = Firestation.builder().station(1).id(1).build();
        Firestation savedFirestation = Firestation.builder().station(1).id(1).build();
        when(firestationDao.save(firestationToUpdate)).thenReturn(savedFirestation);
        firestationService.updateFirestation(firestationToUpdate);
        verify(firestationDao, times(1)).save(any());
    }

    @Test
    public void updateFirestationThrowsException() {
        Exception exception = assertThrows(FirestationsException.class, () -> firestationService.updateFirestation(Firestation.builder().build()));
        assertEquals("Une erreur est survenue durant la mise à jour de la caserne", exception.getMessage());
    }


}
