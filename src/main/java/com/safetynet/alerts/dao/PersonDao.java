package com.safetynet.alerts.dao;

import com.safetynet.alerts.model.Person;
import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface PersonDao extends JpaRepository<Person, Integer> {

    public Person findById(int id);

    void deleteById(int id);

    @Query("Select p FROM Person p WHERE p.city=:city")
    List<Person> getPersonEmail(@Param("city") String city);

}