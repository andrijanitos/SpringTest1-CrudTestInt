package com.andrijanica.SpringTest1.controller;

import com.andrijanica.SpringTest1.exception.ResourceNotFoundException;
import com.andrijanica.SpringTest1.model.dto.response.PersonResponseDto;
import com.andrijanica.SpringTest1.model.entity.Person;
import com.andrijanica.SpringTest1.repository.PersonRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.andrijanica.SpringTest1.exception.ErrorCode.NOT_FOUND;
import static com.andrijanica.SpringTest1.util.TestUtil.readResponse;
import static net.bytebuddy.utility.RandomString.make;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PersonRepository personRepository;

    @AfterEach
    public void tearDown() {
        personRepository.deleteAll();
    }

    @Test
    @SneakyThrows
    public void getAllPersonsTest_empty() {
        String result = mockMvc.perform(get("/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        PersonResponseDto[] personResponse = readResponse(result, PersonResponseDto[].class);

        assertNotNull(personResponse);
        assertEquals(personResponse.length, 0);
    }

    @Test
    @SneakyThrows
    public void getAllPersonsTest_ok() {
        Person person = new Person()
                .setName(make(10))
                .setAge(1 + (int) (Math.random() * 30));
        personRepository.save(person);

        String result = mockMvc.perform(get("/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        PersonResponseDto[] personResponse = readResponse(result, PersonResponseDto[].class);

        assertNotNull(personResponse);
        assertEquals(personResponse.length, 1);
    }

    @Test
    @SneakyThrows
    public void getPersonByIdTest_ok() {
        Person person = new Person()
                .setName(make(10))
                .setAge(1 + (int) (Math.random() * 30));
        person = personRepository.save(person);

        String result = mockMvc.perform(get("/persons/" + person.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        PersonResponseDto personResponse = readResponse(result, PersonResponseDto.class);

        assertNotNull(personResponse);
        assertEquals(personResponse.getId(), person.getId());
        assertEquals(personResponse.getAge(), person.getAge());
        assertEquals(personResponse.getName(), person.getName());
    }

    @Test
    @SneakyThrows
    public void getPersonByIdTest_notFound() {
        Long randomId = (long) (1 + (int) (Math.random() * 100));

        String result = mockMvc.perform(get("/persons/" + randomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();

        var errorResponse = readResponse(result, ResourceNotFoundException.class);

        assertNotNull(errorResponse);
        assertEquals(errorResponse.getMessage(), "Person with id=" + randomId + " not found!");
        assertEquals(errorResponse.getCode(), NOT_FOUND);
    }
}
