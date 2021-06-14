package com.safetynet.alerts.service;

import com.safetynet.alerts.dao.MedicalDao;
import com.safetynet.alerts.exception.FirestationsException;
import com.safetynet.alerts.exception.MedicalsException;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Medical;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MedicalServiceTest {

    @Mock
    MedicalDao medicalDao;

    @InjectMocks
    MedicalService medicalService;

    @Test
    public void listMedical() {
        when(medicalDao.findAll()).thenReturn(List.of(Medical.builder().firstName("Jean").build()));
        List<Medical> medicals = medicalService.listMedical();
        verify(medicalDao, times(1)).findAll();
        assertEquals(medicals.get(0).getFirstName(), "Jean");
    }

    @Test
    public void listMedicalThrowsException() {
        Exception exception = assertThrows(MedicalsException.class, () -> medicalService.listMedical());
        assertEquals("Erreur lors de la récupération des dossiers médicaux", exception.getMessage());
    }

    @Test
    public void displayMedical() {
        when(medicalDao.findById(1)).thenReturn(Medical.builder().id(1).build());
        medicalService.displayMedical(1);
        verify(medicalDao, times(1)).findById(anyInt());
    }

    @Test
    public void displayMedicalThrowsException() {
        Exception exception = assertThrows(MedicalsException.class, () -> medicalService.displayMedical(1));
        assertEquals("Erreur lors de la récupération du dossier médical", exception.getMessage());
    }

    @Test
    public void addMedical() {
        medicalService.addMedical(Medical.builder().id(1).build());
        verify(medicalDao, times(1)).save(any());
    }

    @Test
    public void addMedicalNoContent() {
        Medical medical = Medical.builder().firstName("Jeanne").build();
        ResponseEntity responseEntityOutput = medicalService.addMedical(medical);
        assertEquals(ResponseEntity.noContent().build(), responseEntityOutput);
    }

    @Test
    public void addMedicals() {
        Medical medicalToAdd1 = Medical.builder().build();
        Medical medicalToAdd2 = Medical.builder().build();
        Medical medicalAdded1 = Medical.builder().id(1).build();
        Medical medicalAdded2 = Medical.builder().id(2).build();
        when(medicalDao.save(medicalToAdd1)).thenReturn(medicalAdded1);
        when(medicalDao.save(medicalToAdd2)).thenReturn(medicalAdded2);
        medicalService.addMedicals(List.of(medicalToAdd1, medicalToAdd2));
        verify(medicalDao, times(2)).save(any());
    }

    @Test
    public void addMedicalsThrowsException() {
        List<Medical> medicals = List.of(Medical.builder().build());
        Exception exception = assertThrows(MedicalsException.class, () -> medicalService.addMedicals(medicals));
        assertEquals("Une erreur est survenue durant l'ajout des dossiers médicaux", exception.getMessage());
    }

    @Test
    public void deleteMedical() {
        medicalService.deleteMedical(1);
        verify(medicalDao, times(1)).deleteById(1);
    }

    @Test
    public void deleteMedicalThrowsException() {
        medicalService.deleteMedical(1);
        verify(medicalDao, times(1)).deleteById(anyInt());
        when(medicalDao.findById(1)).thenReturn(Medical.builder().id(1).build());
        Exception exception = assertThrows(MedicalsException.class, () -> medicalService.deleteMedical(1));
        assertEquals("Une erreur est survenue durant la suppression du dossier médical", exception.getMessage());
    }

    @Test
    public void updateMedical() {
        Medical medicalToUpdate = Medical.builder().id(1).build();
        Medical savedMedical = Medical.builder().id(1).build();
        when(medicalDao.save(medicalToUpdate)).thenReturn(savedMedical);
        medicalService.updateMedical(medicalToUpdate);
        verify(medicalDao).save(any());
    }

    @Test
    public void updateMedicalThrowsException() {
        Exception exception = assertThrows(MedicalsException.class, () -> medicalService.updateMedical(Medical.builder().build()));
        assertEquals("Une erreur est survenue durant la mise à jour du dossier médical", exception.getMessage());
    }

}
