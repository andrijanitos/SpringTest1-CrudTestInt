package com.andrijanica.SpringTest1.service;

import com.andrijanica.SpringTest1.controller.PersonController;
import com.andrijanica.SpringTest1.exception.GeneralException;
import com.andrijanica.SpringTest1.exception.ResourceNotFoundException;
import com.andrijanica.SpringTest1.model.dto.response.PersonResponseDto;
import com.andrijanica.SpringTest1.model.entity.Person;
import com.andrijanica.SpringTest1.repository.PersonRepository;
import com.andrijanica.SpringTest1.service.personService.PersonService;
import com.andrijanica.SpringTest1.service.personService.PersonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.andrijanica.SpringTest1.exception.ErrorCode.NOT_FOUND;
import static net.bytebuddy.utility.RandomString.make;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class PersonServiceImplTest {
    @Mock
    private PersonRepository personRepository;

    private PersonService personService;

    private Long randomId;

    @BeforeEach
    void setup() {
        personService = new PersonServiceImpl(personRepository);
        randomId = (long) (1 + (int) (Math.random() * 100));
    }

    @Test
    public void getAllPersonsTest_empty() {
        List<Person> persons = new ArrayList<>();
        when(personRepository.findAll())
                .thenReturn(persons);

        List<Person> result = personService.getAllPersons();

        assertEquals(result.size(), 0);
    }

    @Test
    public void getAllPersonsTest_ok() {
        List<Person> persons = new ArrayList<>();
        persons.add(new Person());
        persons.add(new Person());

        when(personRepository.findAll())
                .thenReturn(persons);

        List<Person> result = personService.getAllPersons();

        assertEquals(result.size(), 2);
    }

    @Test
    public void getPersonByIdTest_ok() {
        List<Person> persons = new ArrayList<>();
        Person person = new Person()
                .setId(randomId)
                .setName(make(10))
                .setAge(1 + (int) (Math.random() * 30));
        persons.add(person);

        when(personRepository.findById(any()))
                .thenReturn(Optional.of(person));

        Person result = personService.getPersonById(person.getId());
        assertNotNull(result);
        assertEquals(result.getId(), person.getId());
        assertEquals(result.getAge(), person.getAge());
        assertEquals(result.getName(), person.getName());
    }

    @Test
    public void getPersonByIdTest_notFound() {
        final String ERROR_MESSAGE = "User not found!";
        when(personRepository.findById(any()))
                .thenThrow(new ResourceNotFoundException(ERROR_MESSAGE));

        var exception = assertThrows(GeneralException.class,
                () -> personService.getPersonById(randomId));

        assertAll(
                () -> assertThat(exception).hasMessage(ERROR_MESSAGE),
                () -> assertThat(exception.getCode()).isEqualTo(NOT_FOUND)
        );
    }
}
