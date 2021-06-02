package com.safetynet.alerts.controller;

import com.safetynet.alerts.mapper.PersonMapper;
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
    public void listMedical() {
        alertController.listMedical();
        verify(alertsService, times(1)).listMedical();
    }

    @Test
    public void displayMedical() {
        alertController.displayMedical(1);
        verify(alertsService, times(1)).displayMedical(1);
    }

    @Test
    public void addMedical() {
        alertController.addMedical(any());
        verify(alertsService, times(1)).addMedical(any());
    }

    @Test
    public void addMedicals() {
        alertController.addMedicals(any());
        verify(alertsService, times(1)).addMedicals(any());
    }

    @Test
    public void deleleMedical() {
        alertController.deleteMedical(1);
        verify(alertsService, times(1)).deleteMedical(1);
    }

    @Test
    public void updateMedical() {
        alertController.updateMedical(any());
        verify(alertsService, times(1)).updateMedical(any());
    }

    @Test
    public void listFirestation() {
        alertController.listFirestation();
        verify(alertsService, times(1)).listFirestation();
    }

    @Test
    public void displayFirestation() {
        alertController.displayFirestation(1);
        verify(alertsService, times(1)).displayFirestation(1);
    }

    @Test
    public void addFirestation() {
        alertController.addFirestation(any());
        verify(alertsService, times(1)).addFirestation(any());
    }

    @Test
    public void addFirestations() {
        alertController.addFirestations(any());
        verify(alertsService, times(1)).addFirestations(any());
    }

    @Test
    public void deleleFirestation() {
        alertController.deleteFirestation(1);
        verify(alertsService, times(1)).deleteFirestation(1);
    }

    @Test
    public void updateFirestation() {
        alertController.updateFirestation(any());
        verify(alertsService, times(1)).updateFirestation(any());
    }

    @Test
    public void listPerson() {
        alertController.listPerson();
        verify(alertsService, times(1)).listPerson();
    }

    @Test
    public void displayPerson() {
        alertController.displayPerson(1);
        verify(alertsService, times(1)).displayPerson(1);
    }

    @Test
    public void addPerson() {
        alertController.addPerson(any());
        verify(alertsService, times(1)).addPerson(any());
    }

    @Test
    public void addPersons() {
        alertController.addPersons(any());
        verify(alertsService, times(1)).addPersons(any());
    }

    @Test
    public void delelePerson() {
        alertController.deletePerson(1);
        verify(alertsService, times(1)).deletePerson(1);
    }

    @Test
    public void updatePerson() {
        alertController.updatePerson(any());
        verify(alertsService, times(1)).updatePerson(any());
    }

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
    public void getOnePersonInformationByNames() {
        alertController.getOnePersonInformationByNames(any(), any());
        verify(alertsService, times(1)).getPersonInformationByNames(any(), any());

    }

    @Test
    public void getPersonsAtStations() {
        alertController.getPersonsAtStations(any());
        verify(alertsService, times(1)).getPersonsAtStations(any());
    }

    @Test
    public void getPersonWithFirestationCoverage() {
        List<PersonMapper> output = alertController.getPersonAtAddressWithFirestationCoverage("1 street");
        verify(alertsService, times(1)).getPersonAtAddressWithFirestationCoverage(anyString());
    }


/*    @Test
    public void getPersonInformation() {
        alertController.getPersonInformation();
        verify(alertsService, times(1)).getPersonInformation();
    }*/

    @Test
    public void getPersonEmail() {
        alertController.getPersonEmail("Culver");
        verify(alertsService, times(1)).getPersonEmail(anyString());
    }
}
