package com.safetynet.alerts.mapper;

import com.safetynet.alerts.model.Medical;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PersonMapperTest {

    @Test
    public void setAndGetPersonMapperData() {
        Medical medical = Medical.builder().birthdate(new SimpleDateFormat("MM/d/yyyy").format(new Date())).build();
        PersonMapper personMapper = PersonMapper.builder()
                .phone("012345").age(10)
                .station(1).address("101 street")
                .email("smith@mail.com")
                .firstName("John").lastName("Smith").medical(medical)
                .build();
        assertEquals(Integer.valueOf(10), personMapper.getAge());
        assertEquals("012345", personMapper.getPhone());
        assertEquals(1, personMapper.getStation());
        assertEquals("101 street", personMapper.getAddress());
        assertEquals("smith@mail.com", personMapper.getEmail());
        assertEquals(medical, personMapper.getMedical());
        assertEquals("John", personMapper.getFirstName());
        assertEquals("Smith", personMapper.getLastName());
        personMapper.setAge(1);
        personMapper.setPhone("123456");
        personMapper.setAddress("102 street");
        personMapper.setEmail("johnny@mail.com");
        personMapper.setMedical(medical);
        personMapper.setFirstName("Johnny");
        personMapper.setLastName("Boyd");
        assertEquals(Integer.valueOf(1), personMapper.getAge());
        assertEquals("123456", personMapper.getPhone());
        assertEquals(1, personMapper.getStation());
        assertEquals("102 street", personMapper.getAddress());
        assertEquals("johnny@mail.com", personMapper.getEmail());
        assertEquals(medical, personMapper.getMedical());
        assertEquals("Johnny", personMapper.getFirstName());
        assertEquals("Boyd", personMapper.getLastName());
    }

}
