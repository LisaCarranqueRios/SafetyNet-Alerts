package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.IAlertsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.util.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlertControllerTest {

    @InjectMocks
    AlertController alertController;

    @Mock
    IAlertsService alertsService;

    @Test
    public void getPersonCoveredByFirestation() throws ParseException {
        alertController.getPersonCoveredByFirestation(1);
        verify(alertsService, times(1)).getPersonCoveredByFirestation(anyInt());
    }

    @Test
    public void getChildrenAtAddress() throws ParseException {
        alertController.getChildrenAtAddress("1 street");
        verify(alertsService, times(1)).getChildrenAtAddress(anyString());
    }

    @Test
    public void getPersonCoveredByFirestationPhoneNumber() {
        alertController.getPersonCoveredByFirestationPhoneNumber(1);
        verify(alertsService, times(1)).getPersonCoveredByFirestationPhoneNumber(anyInt());
    }

    @Test
    public void getPersonWithFirestationCoverage() {
        List<Person> output = alertController.getPersonAtAddressWithFirestationCoverage("1 street");
        verify(alertsService, times(1)).getPersonAtAddressWithFirestationCoverage(anyString());
    }


    @Test
    public void getPersonInformation() {
        alertController.getPersonInformation();
        verify(alertsService, times(1)).getPersonInformation();
    }

    @Test
    public void getPersonEmail() {
        alertController.getPersonEmail("Culver");
        verify(alertsService, times(1)).getPersonEmail(anyString());
    }
}
