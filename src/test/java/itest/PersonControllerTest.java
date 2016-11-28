package itest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isolisduran.springboot.bean.Person;

import itest.config.ConfigServerWithFongoConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { ConfigServerWithFongoConfiguration.class }, properties = {
		"server.port=8980" }, webEnvironment = WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@TestPropertySource(properties = { "spring.data.mongodb.database=test" })
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PersonControllerTest {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private MockMvc mockMvc;

	private ObjectMapper jsonMapper;

	@Before
	public void setUp() {
		jsonMapper = new ObjectMapper();
	}

	@Test
	public void testGetPerson() throws Exception {

		Person personFongo = new Person();
		personFongo.setId(1);
		personFongo.setName("Name1");
		personFongo.setAddress("Address1");
		mongoTemplate.createCollection("person");
		mongoTemplate.insert(personFongo);

		ResultActions resultAction = mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8090/api/person/1"));
		resultAction.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		MvcResult result = resultAction.andReturn();
		Person personResponse = jsonMapper.readValue(result.getResponse().getContentAsString(), Person.class);
		Assert.assertEquals(1, personResponse.getId());
		Assert.assertEquals("Name1", personResponse.getName());
		Assert.assertEquals("Address1", personResponse.getAddress());

	}

	@Test
	public void testCreatePerson() throws Exception {

		Person personRequest = new Person();
		personRequest.setId(1);
		personRequest.setName("Name1");
		personRequest.setAddress("Address1");

		String body = jsonMapper.writeValueAsString(personRequest);

		ResultActions resultAction = mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8090/api/person")
				.contentType(MediaType.APPLICATION_JSON).content(body));
		resultAction.andExpect(MockMvcResultMatchers.status().isCreated());

		Person personFongo = mongoTemplate.findOne(new Query(Criteria.where("id").is(1)), Person.class);
		Assert.assertEquals(personFongo.getName(), "Name1");
		Assert.assertEquals(personFongo.getAddress(), "Address1");
	}
}
