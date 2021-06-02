package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Medical;
import com.safetynet.alerts.model.Person;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

public interface IAlertsService {

    /*
        Methods to manage person, firestation, medical data
     */

    /**
     * Manage persons
     *
     * @return
     */
    public List<Person> listPerson();

    public Person displayPerson(int id);

    public ResponseEntity<Object> addPerson(Person person);

    public void addPersons(List<Person> persons);

    public void deletePerson(int id);

    public void updatePerson(Person person);


    /**
     * Manage firestations
     *
     * @return
     */
    public List<Firestation> listFirestation();

    public Firestation displayFirestation(int id);

    public ResponseEntity<Object> addFirestation(Firestation firestation);

    public void addFirestations(List<Firestation> firestations);

    public void deleteFirestation(int id);

    public void updateFirestation(Firestation firestation);

    /**
     * manage medical
     *
     * @return
     */
    public List<Medical> listMedical();

    public Medical displayMedical(int id);

    public ResponseEntity<Object> addMedical(Medical medical);

    public void addMedicals(List<Medical> medicals);

    public void deleteMedical(int id);

    public void updateMedical(Medical medical);


    /**
     * This person is responsible for getting :
     * - email
     * for each person in the targeted city
     *
     * @param city
     * @return
     * @throws IOException
     */
    public List<String> getPersonEmail(String city);

    /**
     * This person is responsible for getting
     * - person information
     * for a person targeted by lastName and firstName
     *
     * @return
     */
    public List<Person> getPersonInformationByNames(@RequestParam String firstName, @RequestParam String lastName);

    /**
     * This method is responsible for getting
     * - lastName, firstName, age
     * - medical data
     * for persons covered by the firestations targeted by on station number
     *
     * @param station
     * @return
     */
    public List<Person> getPersonAtAddress(int station);

    /**
     * This method is responsible for getting
     * - lastName, firstName, age
     * - medical data
     * for persons covered by the firestations targeted by station number
     *
     * @param ids
     * @return
     */
    public List<Person> getPersonsAtStations(List<Integer> ids);

    /**
     * This method is responsible for getting :
     * - lastName, firstName, phone, age
     * - medical data
     * for persons covered by the firestations targeted by address
     *
     * @param address
     * @return
     */
    public List<Person> getPersonAtAddressWithFirestationCoverage(String address);

    /**
     * This method is responsible for getting :
     * - phone number
     * for persons covered by the firestations targeted by station number
     *
     * @param station
     * @return
     */
    public List<Person> getPersonCoveredByFirestationPhoneNumber(@PathVariable("station") int station);

    /**
     * This method is responsible for getting :
     * - firstName, lastName, age
     * - a list of family members
     * for children covered by the firestations targeted by one address
     *
     * @param address
     * @return
     */
    public List<Object> getChildrenAtAddress(String address);


    /**
     * This method is reponsible for getting :
     * - firstName, lastName, phone
     * - children and adults count
     * covered by firestations targeted by station number
     *
     * @param station
     * @return
     */
    public List<Object> getPersonCoveredByFirestation(int station);

}

