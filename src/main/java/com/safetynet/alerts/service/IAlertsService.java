package com.safetynet.alerts.service;

import com.safetynet.alerts.mapper.PersonMapper;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Medical;
import com.safetynet.alerts.model.Person;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface IAlertsService {

    /**
     * Method to manage person, firestation, medical CRUD
     *
     */

    /**
     * Manage persons
     * @return
     */
    public List<Person> listPerson();

    public Person displayPerson(int id);

    public ResponseEntity<Object> addPerson(Person person);

    public void addPersons(List<Person>  persons);

    public void delelePerson(int id);

    public void updatePerson(Person person);


    /**
     * Manage firestations
     * @return
     */
    public List<Firestation> listFirestation();

    public Firestation displayFirestation(int id);

    public ResponseEntity<Object> addFirestation(Firestation firestation);

    public void addFirestations(List<Firestation> firestations);

    public void deleleFirestation(int id);

    public void updateFirestation(Firestation firestation);

    /**
     * manage medical
     * @return
     */
    public List<Medical> listMedical();

    public Medical displayMedical(int id);

    public ResponseEntity<Object> addMedical(Medical medical);

    public void addMedicals(List<Medical> medicals);

    public void deleleMedical(int id);

    public void updateMedical(Medical medical);


    /**
     *
     */

    /**
     * This person is responsible for getting :
     * - email
     * for each person in the targeted city
     * @param city
     * @return
     * @throws IOException
     */
    public List<PersonMapper> getPersonEmail(String city);

    /**
     * This person is responsible for getting person information for each person
     * @return
     */
    public List<Person> getPersonInformation();

    /**
     * This person is responsible for getting
     * person information
     * for a person targeted by lastName and firstName
     * @param names
     * @return
     */
    public Person getOnePersonInformation(List<String> names);

    /**
     * This method is responsible for getting
     * lastName, firstName, age
     * medical data
     * for person covered by firestations targeted by on station number
     * @param station
     * @param personWithMedication
     * @return
     */
    public List<Person> getPersonAtAddresse(int station, List<Person> personWithMedication);

    /**
     * This method is responsible for getting
     * lastName, firstName, age
     * medical data
     * for person covered by firestations targeted by station number
     * @param ids
     * @return
     */
    public List<Person> getPersonsAtStations(List<Integer> ids);

    /**
     * This method is responsible for getting :
     * lastName, firstName, phone, age
     * medical data
     * for person covered by firestation targeted by address
     * @param address
     * @return
     */
    public List<Person>  getPersonAtAddressWithFirestationCoverage(String address);

    /**
     * This method is responsible for getting :
     * phone number
     * for persons covered by firestation targeted by station number
     * @param station
     * @return
     */
    public List<PersonMapper> getPersonCoveredByFirestationPhoneNumber(@PathVariable("station") int station);

    /**
     * This method is responsible for getting :
     * firstName, lastName, age
     * and a list of family members
     * for children covered by firestation targeted by one address
     * @param address
     * @return
     */
    public HashMap<List<Person>, List<Person>>  getChildrenAtAddress(String address);


    /**
     * This method is reponsible for getting :
     * firstName, lastName, phone
     * children and adults count
     * covered by firestations targeted by station number
     * @param station
     * @return
     */
    public List<Object> getPersonCoveredByFirestation(int station);

}

