package com.safetynet.alerts.dao;

import com.safetynet.alerts.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This interface contains methods to manage person data
 */
@Repository
public interface PersonDao extends JpaRepository<Person, Integer> {

    public Person findById(int id);

    void deleteById(int id);

    @Query("Select p.email FROM Person p WHERE p.city=:city")
    List<String> getPersonEmail(@Param("city") String city);

    @Query("Select p FROM Person p WHERE p.firstName=:firstName and p.lastName=:lastName")
    List<Person> findByFirstNameAndLastName(String firstName, String lastName);

    @Query("Select p FROM Person p  join p.firestation f where f.station=:station")
    List<Person> findByStation(int station);

    @Query("Select p FROM Person p  join p.firestation f where f.address=:address")
    List<Person> findByAddress(String address);
}
