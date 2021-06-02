package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

public interface IAlertsService {

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

