package com.safetynet.alerts.controller;

import com.safetynet.alerts.service.IAlertsService;
import com.safetynet.alerts.service.IMedicalService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MedicalControllerTest {

    @InjectMocks
    MedicalController medicalController;

    @Mock
    IMedicalService medicalService;

    @Test
    public void listMedical() {
        medicalController.listMedical();
        verify(medicalService, times(1)).listMedical();
    }

    @Test
    public void displayMedical() {
        medicalController.displayMedical(1);
        verify(medicalService, times(1)).displayMedical(1);
    }

    @Test
    public void addMedical() {
        medicalController.addMedical(any());
        verify(medicalService, times(1)).addMedical(any());
    }

    @Test
    public void addMedicals() {
        medicalController.addMedicals(any());
        verify(medicalService, times(1)).addMedicals(any());
    }

    @Test
    public void deleleMedical() {
        medicalController.deleteMedical(1);
        verify(medicalService, times(1)).deleteMedical(1);
    }

    @Test
    public void updateMedical() {
        medicalController.updateMedical(any());
        verify(medicalService, times(1)).updateMedical(any());
    }



}
