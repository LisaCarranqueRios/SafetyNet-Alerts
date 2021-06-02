package com.safetynet.alerts.service;

import com.safetynet.alerts.dao.MedicalDao;
import com.safetynet.alerts.model.Medical;
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
public class MedicalServiceTest {

    @Mock
    MedicalDao medicalDao;

    @InjectMocks
    MedicalService medicalService;

    @Test
    public void listMedical() {
        medicalService.listMedical();
        verify(medicalDao, times(1)).findAll();
    }
    @Test
    public void displayMedical() {
        medicalService.displayMedical(1);
        verify(medicalDao, times(1)).findById(anyInt());
    }
    @Test
    public void addMedical() {
        medicalService.addMedical(Medical.builder().id(1).build());
        verify(medicalDao, times(1)).save(any());
    }
    @Test
    public void addMedicals() {
        Medical medical = Medical.builder().build();
        Medical medical2 = Medical.builder().build();
        medicalService.addMedicals(List.of(medical, medical2));
        verify(medicalDao, times(2)).save(any());
    }
    @Test
    public void deleteMedical() {
        medicalService.deleteMedical(1);
        verify(medicalDao, times(1)).deleteById(1);
    }
    @Test
    public void updateMedical() {
        medicalService.updateMedical(Medical.builder().build());
        verify(medicalDao).save(any());
    }

}
