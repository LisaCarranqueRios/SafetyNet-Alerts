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
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {AlertController.class, IAlertsService.class, AlertsService.class, AlertsUtils.class})
@AutoConfigureMockMvc
@EnableAutoConfiguration
@EnableJpaRepositories(basePackageClasses = {MedicalDao.class, PersonDao.class, FirestationDao.class})
@EntityScan(basePackageClasses = {Medical.class, Firestation.class, Person.class})
public class AlertControllerFetchDataIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetPersonEmail() throws Exception {
        String jsonbodyFirestation = "{ \"address\":\"1509 Erqy St\", \"station\":\"100\" }";
        MvcResult mvcResultFirestation = this.mockMvc.perform(post("/firestation").content(jsonbodyFirestation).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        String savedLocationFirestation = mvcResultFirestation.getResponse().getHeaders("Location").get(0).toString();
        String idFirestation = savedLocationFirestation.replaceAll("[^0-9]", "");
        String jsonbodyMedical = "{ \"firstName\":\"John\", \"lastName\":\"Boyd\", \"birthdate\":\"08/06/1945\", \"medications\":[\"tradoxidine:400mg\"], \"allergies\":[] }";
        MvcResult mvcResultMedical = this.mockMvc.perform(post("/medical").content(jsonbodyMedical).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        String savedLocationMedical = mvcResultMedical.getResponse().getHeaders("Location").get(0).toString();
        String idMedical = savedLocationMedical.replaceAll("[^0-9]", "");

        String jsonbodyPerson = "{\n" +
                "        \"firstName\": \"John\",\n" +
                "        \"lastName\": \"Boyd\",\n" +
                "        \"phone\": \"841-874-6512\",\n" +
                "        \"email\": \"jaboyd@email.com\",\n" +
                "        \"address\": \"1509 Erqy St\",\n" +
                "        \"city\": \"Erqy\",\n" +
                "        \"zip\": \"96451\",\n" +
                "        \"station\": null,\n" +
                "        \"medical\": {\n" +
                "            \"id\": "+ idMedical +"},\n" +
                "        \"firestation\": {\n" +
                "            \"id\":"+ idFirestation +"}\n" +
                "    }";
        MvcResult mvcResultPerson = this.mockMvc.perform(post("/person").content(jsonbodyPerson).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        MvcResult mvcResultPersonInfo = this.mockMvc.perform(get("/communityEmail/{city}", "Erqy"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$.size()").value(1))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0]").value("jaboyd@email.com"))
                .andReturn();
     }

    @Test
    public void testGetOnePersonInformationByNames() throws Exception {
        String jsonbodyFirestation = "{ \"address\":\"1509 Culver St\", \"station\":\"30\" }";
        MvcResult mvcResultFirestation = this.mockMvc.perform(post("/firestation").content(jsonbodyFirestation).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        String savedLocationFirestation = mvcResultFirestation.getResponse().getHeaders("Location").get(0).toString();
        String idFirestation = savedLocationFirestation.replaceAll("[^0-9]", "");
        String jsonbodyMedical = "{ \"firstName\":\"Johnny\", \"lastName\":\"Boydy\", \"birthdate\":\"08/06/1945\", \"medications\":[\"tradoxidine:400mg\"], \"allergies\":[] }";
        MvcResult mvcResultMedical = this.mockMvc.perform(post("/medical").content(jsonbodyMedical).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        String savedLocationMedical = mvcResultMedical.getResponse().getHeaders("Location").get(0).toString();
        String idMedical = savedLocationMedical.replaceAll("[^0-9]", "");

        String jsonbodyPerson = "{\n" +
                "        \"firstName\": \"Johnny\",\n" +
                "        \"lastName\": \"Boydy\",\n" +
                "        \"phone\": \"841-874-6512\",\n" +
                "        \"email\": \"jaboydy@email.com\",\n" +
                "        \"address\": \"1509 Culver St\",\n" +
                "        \"city\": \"Culver\",\n" +
                "        \"zip\": \"97451\",\n" +
                "        \"station\": null,\n" +
                "        \"medical\": {\n" +
                "            \"id\": "+ idMedical +"},\n" +
                "        \"firestation\": {\n" +
                "            \"id\":"+ idFirestation +"}\n" +
                "    }";
        MvcResult mvcResultPerson = this.mockMvc.perform(post("/person").content(jsonbodyPerson).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        MvcResult mvcResultPersonInfo = this.mockMvc.perform(get("/personInfo?firstName=Johnny&lastName=Boydy"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$.size()").value(1))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].firstName").value("Johnny"))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].lastName").value("Boydy"))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].email").value("jaboydy@email.com"))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].medical").isNotEmpty())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].medical.id").value(idMedical))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].phone").doesNotExist())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].address").doesNotExist())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].city").doesNotExist())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].zip").doesNotExist())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].firestation").doesNotExist())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].station").doesNotExist())
                .andReturn();
    }


    @Test
    public void testGetPersonsAtStations() throws Exception {
        String jsonbodyFirestation = "{ \"address\":\"1509 Culver St\", \"station\":\"40\" }";
        MvcResult mvcResultFirestation = this.mockMvc.perform(post("/firestation").content(jsonbodyFirestation).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        String savedLocationFirestation = mvcResultFirestation.getResponse().getHeaders("Location").get(0).toString();
        String idFirestation = savedLocationFirestation.replaceAll("[^0-9]", "");
        String jsonbodyMedical = "{ \"firstName\":\"John\", \"lastName\":\"Boyd\", \"birthdate\":\"08/06/1945\", \"medications\":[\"tradoxidine:400mg\"], \"allergies\":[] }";
        MvcResult mvcResultMedical = this.mockMvc.perform(post("/medical").content(jsonbodyMedical).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        String savedLocationMedical = mvcResultMedical.getResponse().getHeaders("Location").get(0).toString();
        String idMedical = savedLocationMedical.replaceAll("[^0-9]", "");

        String jsonbodyPerson = "{\n" +
                "        \"firstName\": \"John\",\n" +
                "        \"lastName\": \"Boyd\",\n" +
                "        \"phone\": \"841-874-6512\",\n" +
                "        \"email\": \"jaboyd@email.com\",\n" +
                "        \"address\": \"1509 Culver St\",\n" +
                "        \"city\": \"Culver\",\n" +
                "        \"zip\": \"97451\",\n" +
                "        \"station\": null,\n" +
                "        \"medical\": {\n" +
                "            \"id\": "+ idMedical +"},\n" +
                "        \"firestation\": {\n" +
                "            \"id\":"+ idFirestation +"}\n" +
                "    }";
        MvcResult mvcResultPerson1 = this.mockMvc.perform(post("/person").content(jsonbodyPerson).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        String jsonbodyFirestation2 = "{ \"address\":\"1509 Culver St\", \"station\":\"42\" }";
        MvcResult mvcResultFirestation2 = this.mockMvc.perform(post("/firestation").content(jsonbodyFirestation2).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        String savedLocationFirestation2 = mvcResultFirestation2.getResponse().getHeaders("Location").get(0).toString();
        String idFirestation2 = savedLocationFirestation2.replaceAll("[^0-9]", "");
        String jsonbodyMedical2 = "{ \"firstName\":\"John\", \"lastName\":\"Boyd\", \"birthdate\":\"08/06/1945\", \"medications\":[\"tradoxidine:400mg\"], \"allergies\":[] }";
        MvcResult mvcResultMedical2 = this.mockMvc.perform(post("/medical").content(jsonbodyMedical2).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        String savedLocationMedical2 = mvcResultMedical2.getResponse().getHeaders("Location").get(0).toString();
        String idMedical2 = savedLocationMedical2.replaceAll("[^0-9]", "");

        String jsonbodyPerson2 = "{\n" +
                "        \"firstName\": \"Jeanne\",\n" +
                "        \"lastName\": \"Dupont\",\n" +
                "        \"phone\": \"841-874-6513\",\n" +
                "        \"email\": \"jd@email.com\",\n" +
                "        \"address\": \"1509 Culver St\",\n" +
                "        \"city\": \"Culver\",\n" +
                "        \"zip\": \"97451\",\n" +
                "        \"station\": null,\n" +
                "        \"medical\": {\n" +
                "            \"id\": "+ idMedical2 +"},\n" +
                "        \"firestation\": {\n" +
                "            \"id\":"+ idFirestation2 +"}\n" +
                "    }";
        MvcResult mvcResultPerson2 = this.mockMvc.perform(post("/person").content(jsonbodyPerson2).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        MvcResult mvcResultPersonInfo = this.mockMvc.perform(get("/flood/ids?ids=40,42"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$.size()").value(2))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].firstName").value("John"))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].lastName").value("Boyd"))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].phone").value("841-874-6512"))
                 .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].address").value("1509 Culver St"))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].age").exists())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].medical").isNotEmpty())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].medical.id").value(idMedical))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].firestation").doesNotExist())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].city").doesNotExist())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].zip").doesNotExist())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].station").doesNotExist())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].email").doesNotExist())
                 .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[1].firstName").value("Jeanne"))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[1].lastName").value("Dupont"))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[1].phone").value("841-874-6513"))
                 .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[1].address").value("1509 Culver St"))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[1].age").exists())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[1].medical").isNotEmpty())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[1].medical.id").value(idMedical2))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[1].firestation").doesNotExist())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[1].city").doesNotExist())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[1].zip").doesNotExist())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[1].station").doesNotExist())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[1].email").doesNotExist())
                .andReturn();

    }

    @Test
    public void testGetPersonAtAddressWithFirestationCoverage() throws Exception {
        String jsonbodyFirestation = "{ \"address\":\"1510 Culver St\", \"station\":\"30\" }";
        MvcResult mvcResultFirestation = this.mockMvc.perform(post("/firestation").content(jsonbodyFirestation).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        String savedLocationFirestation = mvcResultFirestation.getResponse().getHeaders("Location").get(0).toString();
        String idFirestation = savedLocationFirestation.replaceAll("[^0-9]", "");
        String jsonbodyMedical = "{ \"firstName\":\"John\", \"lastName\":\"Boyd\", \"birthdate\":\"08/06/1945\", \"medications\":[\"tradoxidine:400mg\"], \"allergies\":[] }";
        MvcResult mvcResultMedical = this.mockMvc.perform(post("/medical").content(jsonbodyMedical).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        String savedLocationMedical = mvcResultMedical.getResponse().getHeaders("Location").get(0).toString();
        String idMedical = savedLocationMedical.replaceAll("[^0-9]", "");

        String jsonbodyPerson = "{\n" +
                "        \"firstName\": \"John\",\n" +
                "        \"lastName\": \"Boyd\",\n" +
                "        \"phone\": \"841-874-6512\",\n" +
                "        \"email\": \"jaboyd@email.com\",\n" +
                "        \"address\": \"1510 Culver St\",\n" +
                "        \"city\": \"Culver\",\n" +
                "        \"zip\": \"97451\",\n" +
                "        \"station\": null,\n" +
                "        \"medical\": {\n" +
                "            \"id\": "+ idMedical +"},\n" +
                "        \"firestation\": {\n" +
                "            \"id\":"+ idFirestation +"}\n" +
                "    }";
        MvcResult mvcResultPerson = this.mockMvc.perform(post("/person").content(jsonbodyPerson).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        MvcResult mvcResultPersonInfo = this.mockMvc.perform(get("/fire/1510 Culver St"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$.size()").value(1))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].firstName").value("John"))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].lastName").value("Boyd"))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].phone").value("841-874-6512"))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].address").value("1510 Culver St"))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].age").exists())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].medical").isNotEmpty())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].medical.id").value(idMedical))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].email").doesNotExist())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].city").doesNotExist())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].zip").doesNotExist())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].station").doesNotExist())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].firestation").doesNotExist())
                .andReturn();
    }


    @Test
    public void testGetPersonCoveredByFirestationPhoneNumber() throws Exception {
        String jsonbodyFirestation = "{ \"address\":\"1509 Culver St\", \"station\":\"60\" }";
        MvcResult mvcResultFirestation = this.mockMvc.perform(post("/firestation").content(jsonbodyFirestation).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        String savedLocationFirestation = mvcResultFirestation.getResponse().getHeaders("Location").get(0).toString();
        String idFirestation = savedLocationFirestation.replaceAll("[^0-9]", "");
        String jsonbodyMedical = "{ \"firstName\":\"John\", \"lastName\":\"Boyd\", \"birthdate\":\"08/06/1945\", \"medications\":[\"tradoxidine:400mg\"], \"allergies\":[] }";
        MvcResult mvcResultMedical = this.mockMvc.perform(post("/medical").content(jsonbodyMedical).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        String savedLocationMedical = mvcResultMedical.getResponse().getHeaders("Location").get(0).toString();
        String idMedical = savedLocationMedical.replaceAll("[^0-9]", "");

        String jsonbodyPerson = "{\n" +
                "        \"firstName\": \"John\",\n" +
                "        \"lastName\": \"Boyd\",\n" +
                "        \"phone\": \"841-874-6512\",\n" +
                "        \"email\": \"jaboyd@email.com\",\n" +
                "        \"address\": \"1509 Culver St\",\n" +
                "        \"city\": \"Culver\",\n" +
                "        \"zip\": \"97451\",\n" +
                "        \"station\": null,\n" +
                "        \"medical\": {\n" +
                "            \"id\": "+ idMedical +"},\n" +
                "        \"firestation\": {\n" +
                "            \"id\":"+ idFirestation +"}\n" +
                "    }";
        MvcResult mvcResultPerson = this.mockMvc.perform(post("/person").content(jsonbodyPerson).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        MvcResult mvcResultPersonInfo = this.mockMvc.perform(get("/phoneAlert/60"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$.size()").value(1))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].firstName").value("John"))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].lastName").value("Boyd"))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].phone").value("841-874-6512"))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].address").doesNotExist())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].age").doesNotExist())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].medical").doesNotExist())
                 .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].email").doesNotExist())
                 .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].city").doesNotExist())
                 .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].zip").doesNotExist())
                 .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].firestation").doesNotExist())
                 .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].station").doesNotExist())
                .andReturn();
    }


    @Test
    public void testGetChildrenAtAddress() throws Exception {
        String jsonbodyFirestation = "{ \"address\":\"1511 Culver St\", \"station\":\"30\" }";
        MvcResult mvcResultFirestation = this.mockMvc.perform(post("/firestation").content(jsonbodyFirestation).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        String savedLocationFirestation = mvcResultFirestation.getResponse().getHeaders("Location").get(0).toString();
        String idFirestation = savedLocationFirestation.replaceAll("[^0-9]", "");
        String jsonbodyMedical = "{ \"firstName\":\"John\", \"lastName\":\"Boyd\", \"birthdate\":\"08/06/1945\", \"medications\":[\"tradoxidine:400mg\"], \"allergies\":[] }";
        MvcResult mvcResultMedical = this.mockMvc.perform(post("/medical").content(jsonbodyMedical).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        String savedLocationMedical = mvcResultMedical.getResponse().getHeaders("Location").get(0).toString();
        String idMedical = savedLocationMedical.replaceAll("[^0-9]", "");

        String jsonbodyPerson = "{\n" +
                "        \"firstName\": \"John\",\n" +
                "        \"lastName\": \"Boyd\",\n" +
                "        \"phone\": \"841-874-6512\",\n" +
                "        \"email\": \"jaboyd@email.com\",\n" +
                "        \"address\": \"1511 Culver St\",\n" +
                "        \"city\": \"Culver\",\n" +
                "        \"zip\": \"97451\",\n" +
                "        \"station\": null,\n" +
                "        \"medical\": {\n" +
                "            \"id\": "+ idMedical +"},\n" +
                "        \"firestation\": {\n" +
                "            \"id\":"+ idFirestation +"}\n" +
                "    }";
        MvcResult mvcResultPerson = this.mockMvc.perform(post("/person").content(jsonbodyPerson).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        String jsonbodyFirestation2 = "{ \"address\":\"1511 Culver St\", \"station\":\"32\" }";
        MvcResult mvcResultFirestation2 = this.mockMvc.perform(post("/firestation").content(jsonbodyFirestation2).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        String savedLocationFirestation2 = mvcResultFirestation2.getResponse().getHeaders("Location").get(0).toString();
        String idFirestation2 = savedLocationFirestation2.replaceAll("[^0-9]", "");
        String jsonbodyMedical2 = "{ \"firstName\":\"Jeanne\", \"lastName\":\"Dupont\", \"birthdate\":\""+ new SimpleDateFormat("MM/d/yyyy").format(new Date()) +"\", \"medications\":[\"tradoxidine:400mg\"], \"allergies\":[] }";
         MvcResult mvcResultMedical2 = this.mockMvc.perform(post("/medical").content(jsonbodyMedical2).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        String savedLocationMedical2 = mvcResultMedical2.getResponse().getHeaders("Location").get(0).toString();
        String idMedical2 = savedLocationMedical2.replaceAll("[^0-9]", "");

        String jsonbodyPerson2 = "{\n" +
                "        \"firstName\": \"Jeanne\",\n" +
                "        \"lastName\": \"Dupont\",\n" +
                "        \"phone\": \"841-874-6512\",\n" +
                "        \"email\": \"jd@email.com\",\n" +
                "        \"address\": \"1511 Culver St\",\n" +
                "        \"city\": \"Culver\",\n" +
                "        \"zip\": \"97451\",\n" +
                "        \"station\": null,\n" +
                "        \"medical\": {\n" +
                "            \"id\": "+ idMedical2 +"},\n" +
                "        \"firestation\": {\n" +
                "            \"id\":"+ idFirestation2 +"}\n" +
                "    }";
        MvcResult mvcResultPerson2 = this.mockMvc.perform(post("/person").content(jsonbodyPerson2).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        MvcResult mvcResultPersonInfo = this.mockMvc.perform(get("/childAlert/1511 Culver St"))
                .andDo(print())
                .andExpect(status().isOk())

                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$.size()").value(2))

                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0]").isArray())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[1]").isArray())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].size()").value(2))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[1].size()").value(2))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0][0]").value("children list"))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[1][0]").value("adult house members"))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0][1].firstName").value("Jeanne"))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[1][1].firstName").value("John"))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0][1].lastName").value("Dupont"))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[1][1].lastName").value("Boyd"))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0][1].age").value("0"))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[1][1].age").exists())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[1][0].email").doesNotExist())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[1][0].phone").doesNotExist())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[1][1].phone").doesNotExist())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[1][0].city").doesNotExist())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[1][1].city").doesNotExist())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[1][0].zip").doesNotExist())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[1][1].zip").doesNotExist())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[1][0].address").doesNotExist())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[1][1].address").doesNotExist())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[1][0].station").doesNotExist())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[1][1].station").doesNotExist())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[1][0].medical").doesNotExist())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[1][1].medical").doesNotExist())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[1][0].firestation").doesNotExist())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[1][1].firestation").doesNotExist())
                .andReturn();
     }



    @Test
    public void getPersonCoveredByFirestation() throws Exception {
        String jsonbodyFirestation = "{ \"address\":\"1509 Culver St\", \"station\":\"30\" }";
        MvcResult mvcResultFirestation = this.mockMvc.perform(post("/firestation").content(jsonbodyFirestation).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        String savedLocationFirestation = mvcResultFirestation.getResponse().getHeaders("Location").get(0).toString();
        String idFirestation = savedLocationFirestation.replaceAll("[^0-9]", "");
        String jsonbodyMedical = "{ \"firstName\":\"John\", \"lastName\":\"Boyd\", \"birthdate\":\"08/06/1945\", \"medications\":[\"tradoxidine:400mg\"], \"allergies\":[] }";
        MvcResult mvcResultMedical = this.mockMvc.perform(post("/medical").content(jsonbodyMedical).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        String savedLocationMedical = mvcResultMedical.getResponse().getHeaders("Location").get(0).toString();
        String idMedical = savedLocationMedical.replaceAll("[^0-9]", "");

        String jsonbodyPerson = "{\n" +
                "        \"firstName\": \"John\",\n" +
                "        \"lastName\": \"Boyd\",\n" +
                "        \"phone\": \"841-874-6512\",\n" +
                "        \"email\": \"jaboyd@email.com\",\n" +
                "        \"address\": \"1509 Culver St\",\n" +
                "        \"city\": \"Culver\",\n" +
                "        \"zip\": \"97451\",\n" +
                "        \"station\": null,\n" +
                "        \"medical\": {\n" +
                "            \"id\": "+ idMedical +"},\n" +
                "        \"firestation\": {\n" +
                "            \"id\":"+ idFirestation +"}\n" +
                "    }";
        MvcResult mvcResultPerson = this.mockMvc.perform(post("/person").content(jsonbodyPerson).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        MvcResult mvcResultPersonInfo = this.mockMvc.perform(get("/firestation/?stationNumber=30"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$.size()").value(2))

                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0]").isArray())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[1]").isArray())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].size()").value(1))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[1].size()").value(4))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0][0].firstName").value("John"))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0][0].lastName").value("Boyd"))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0][0].phone").value("841-874-6512"))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0][0].address").value("1509 Culver St"))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0][0].station").value("30"))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[1][0]").value("adultCount"))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[1][1]").value("1"))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[1][2]").value("childCount"))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[1][3]").value("0"))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0][0].email").doesNotExist())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0][0].city").doesNotExist())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0][0].zip").doesNotExist())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0][0].medical").doesNotExist())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0][0].firestation").doesNotExist())
                .andReturn();
    }


}
