package com.safetynet.alerts.controller;

import com.safetynet.alerts.service.IPersonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {

    @InjectMocks
    PersonController personController;

    @Mock
    IPersonService personService;

    @Test
    public void listPerson() {
        personController.listPerson();
        verify(personService, times(1)).listPerson();
    }

    @Test
    public void displayPerson() {
        personController.displayPerson(1);
        verify(personService, times(1)).displayPerson(1);
    }

    @Test
    public void addPerson() {
        personController.addPerson(any());
        verify(personService, times(1)).addPerson(any());
    }

    @Test
    public void addPersons() {
        personController.addPersons(any());
        verify(personService, times(1)).addPersons(any());
    }

    @Test
    public void delelePerson() {
        personController.deletePerson(1);
        verify(personService, times(1)).deletePerson(1);
    }

    @Test
    public void updatePerson() {
        personController.updatePerson(any());
        verify(personService, times(1)).updatePerson(any());
    }


}
