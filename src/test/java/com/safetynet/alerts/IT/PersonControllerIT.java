package com.safetynet.alerts.IT;

import com.safetynet.alerts.controller.AlertController;
import com.safetynet.alerts.controller.FirestationController;
import com.safetynet.alerts.controller.MedicalController;
import com.safetynet.alerts.controller.PersonController;
import com.safetynet.alerts.dao.FirestationDao;
import com.safetynet.alerts.dao.MedicalDao;
import com.safetynet.alerts.dao.PersonDao;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Medical;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.*;
import com.safetynet.alerts.utils.AlertsUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {AlertController.class, FirestationController.class,
        MedicalController.class, PersonController.class,
        IMedicalService.class, MedicalService.class,
        IPersonService.class, PersonService.class,
        IFirestationService.class, FirestationService.class,
        IAlertsService.class, AlertsService.class, AlertsUtils.class})
@AutoConfigureMockMvc
@EnableAutoConfiguration
@EnableJpaRepositories(basePackageClasses = {MedicalDao.class, PersonDao.class, FirestationDao.class})
@EntityScan(basePackageClasses = {Medical.class, Firestation.class, Person.class})
public class PersonControllerIT {

    @Autowired
    private MockMvc mockMvc;

    /*
  Person
   */
    @Test
    public void testListPerson() throws Exception {
        String jsonbody = "{ \"firstName\":\"John\", \"lastName\":\"Boyd\", \"address\":\"1509 Culver St\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-6512\", \"email\":\"jaboyd@email.com\" }";
        MvcResult mvcResult = this.mockMvc.perform(post("/person").content(jsonbody).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        MvcResult mvcResult2 = this.mockMvc.perform(get("/persons"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testDisplayPerson() throws Exception {
        String jsonbody = "{ \"firstName\":\"John\", \"lastName\":\"Boyd\", \"address\":\"1509 Culver St\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-6512\", \"email\":\"jaboyd@email.com\" }";
        MvcResult mvcResult = this.mockMvc.perform(post("/person").content(jsonbody).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        String savedLocation = mvcResult.getResponse().getHeaders("Location").get(0).toString();
        String id = savedLocation.replaceAll("[^0-9]", "");
        MvcResult mvcResult2 = this.mockMvc.perform(get("/persons/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testAddPerson() throws Exception {
        String jsonbody = "{ \"firstName\":\"John\", \"lastName\":\"Boyd\", \"address\":\"1509 Culver St\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-6512\", \"email\":\"jaboyd@email.com\" }";
        MvcResult mvcResult = this.mockMvc.perform(post("/person").content(jsonbody).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    public void testAddPersons() throws Exception {
        String jsonbody = "[{ \"firstName\":\"John\", \"lastName\":\"Boyd\", \"address\":\"1509 Culver St\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-6512\", \"email\":\"jaboyd@email.com\" }," +
                "    { \"firstName\":\"Jacob\", \"lastName\":\"Boyd\", \"address\":\"1509 Culver St\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-6513\", \"email\":\"drk@email.com\" }]";
        MvcResult mvcResult = this.mockMvc.perform(post("/persons").content(jsonbody).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testDeletePersons() throws Exception {
        String jsonbody = "{ \"id\":\"100\", \"firstName\":\"John\", \"lastName\":\"Boyd\", \"address\":\"1509 Culver St\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-6512\", \"email\":\"jaboyd@email.com\" }";
        MvcResult mvcResult = this.mockMvc.perform(post("/person").content(jsonbody).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        String savedLocation = mvcResult.getResponse().getHeaders("Location").get(0).toString();
        String id = savedLocation.replaceAll("[^0-9]", "");
        MvcResult mvcResult2 = this.mockMvc.perform(delete("/persons/{id}", id).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testUpdatePersons() throws Exception {
        String jsonbody = "{ \"firstName\":\"John\", \"lastName\":\"Boyd\", \"address\":\"1509 Culver St\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-6512\", \"email\":\"jaboyd@email.com\" }";
        MvcResult mvcResult = this.mockMvc.perform(put("/persons").content(jsonbody).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }


}
