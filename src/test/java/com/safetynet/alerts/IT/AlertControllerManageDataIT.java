package com.safetynet.alerts.IT;

import com.safetynet.alerts.controller.AlertController;
import com.safetynet.alerts.dao.FirestationDao;
import com.safetynet.alerts.dao.MedicalDao;
import com.safetynet.alerts.dao.PersonDao;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Medical;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.AlertsService;
import com.safetynet.alerts.service.IAlertsService;
import com.safetynet.alerts.utils.AlertsUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {AlertController.class, IAlertsService.class, AlertsService.class, AlertsUtils.class})
@AutoConfigureMockMvc
@EnableAutoConfiguration
@EnableJpaRepositories(basePackageClasses = {MedicalDao.class, PersonDao.class, FirestationDao.class})
@EntityScan(basePackageClasses = {Medical.class, Firestation.class, Person.class})
public class AlertControllerManageDataIT {

    @Autowired
    private MockMvc mockMvc;

    /*
    Person
     */
    @Test
    public void testListPerson() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/persons"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        assertEquals("application/json",
                mvcResult.getResponse().getContentType());
    }

    @Test
    public void testDisplayPerson() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/persons/1"))
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

    /*
    Firestation
     */
    @Test
    public void testListFirestation() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/firestations"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        assertEquals("application/json",
                mvcResult.getResponse().getContentType());
    }

    @Test
    public void testDisplayFirestation() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/firestations/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testAddFirestation() throws Exception {
        String jsonbody = "{ \"address\":\"1509 Culver St\", \"station\":\"30\" }";
        MvcResult mvcResult = this.mockMvc.perform(post("/firestation").content(jsonbody).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    public void testAddFirestations() throws Exception {
        String jsonbody = " [{ \"address\":\"1509 Culver St\", \"station\":\"3\" },\n" +
                "    { \"address\":\"29 15th St\", \"station\":\"2\" }]";
        MvcResult mvcResult = this.mockMvc.perform(post("/persons").content(jsonbody).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testDeleteFirestation() throws Exception {
        String jsonbody = "{ \"id\":\"100\", \"address\":\"1509 Culver St\", \"station\":\"30\" }";
        MvcResult mvcResult = this.mockMvc.perform(post("/firestation").content(jsonbody).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        String savedLocation = mvcResult.getResponse().getHeaders("Location").get(0).toString();
        String id = savedLocation.replaceAll("[^0-9]", "");
        MvcResult mvcResult2 = this.mockMvc.perform(delete("/firestations/{id}", id).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testUpdateFirestation() throws Exception {
        String jsonbody = "{ \"address\":\"1509 Culver St\", \"station\":\"30\" }";
        MvcResult mvcResult = this.mockMvc.perform(put("/firestations").content(jsonbody).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    /*
    Medical data
     */
    @Test
    public void testListMedical() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/medicals"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        assertEquals("application/json",
                mvcResult.getResponse().getContentType());
    }

    @Test
    public void testDisplayMedical() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/medicals/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testAddMedical() throws Exception {
        String jsonbody = "{ \"firstName\":\"Eric\", \"lastName\":\"Cadigan\", \"birthdate\":\"08/06/1945\", \"medications\":[\"tradoxidine:400mg\"], \"allergies\":[] }";
        MvcResult mvcResult = this.mockMvc.perform(post("/medical").content(jsonbody).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    public void testAddMedicals() throws Exception {
        String jsonbody = "[{ \"firstName\":\"John\", \"lastName\":\"Boyd\", \"birthdate\":\"03/06/1984\", \"medications\":[\"aznol:350mg\", \"hydrapermazol:100mg\"], \"allergies\":[\"nillacilan\"] }," +
                "    { \"firstName\":\"Jacob\", \"lastName\":\"Boyd\", \"birthdate\":\"03/06/1989\", \"medications\":[\"pharmacol:5000mg\", \"terazine:10mg\", \"noznazol:250mg\"], \"allergies\":[] }]";
        MvcResult mvcResult = this.mockMvc.perform(post("/medicals").content(jsonbody).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }


    @Test
    public void testDeleteMedical() throws Exception {
        String jsonbody = "{ \"id\":\"100\", \"firstName\":\"Eric\", \"lastName\":\"Cadigan\", \"birthdate\":\"08/06/1945\", \"medications\":[\"tradoxidine:400mg\"], \"allergies\":[] }";
        MvcResult mvcResult = this.mockMvc.perform(post("/medical").content(jsonbody).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        String savedLocation = mvcResult.getResponse().getHeaders("Location").get(0).toString();
        String id = savedLocation.replaceAll("[^0-9]", "");
        MvcResult mvcResult2 = this.mockMvc.perform(delete("/medicals/{id}", id).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testUpdateMedical() throws Exception {
        String jsonbody = "    {\"id\" : \"24\", \"firstName\":\"Eric\", \"lastName\":\"Cadigan\", \"birthdate\":\"08/06/1945\", \"medications\":[\"tradoxidine:400mg\"], \"allergies\":[] }";
        MvcResult mvcResult = this.mockMvc.perform(put("/medicals").content(jsonbody).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }


    @Test
    public void testGetPersonEmail() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/communityEmail/{city}", "Culver"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

}
