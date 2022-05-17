package com.andrijanica.SpringTest1.controller;

import com.andrijanica.SpringTest1.model.dto.request.PersonCreateRequestDto;
import com.andrijanica.SpringTest1.model.dto.request.PersonUpdateRequestDto;
import com.andrijanica.SpringTest1.model.dto.response.PersonResponseDto;
import com.andrijanica.SpringTest1.model.entity.Person;
import com.andrijanica.SpringTest1.service.personService.PersonService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for {@link Person} related operations.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/persons")
public class PersonController {

    private final PersonService personService;
    private final ModelMapper modelMapper;

    @GetMapping()
    public List<PersonResponseDto> getAllPersons() {
        List<Person> persons = personService.getAllPersons();

        return persons.stream()
                .map(person -> modelMapper.map(person, PersonResponseDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PersonResponseDto getPersonById(@PathVariable Long id) {
        Person person = personService.getPersonById(id);

        return modelMapper.map(person, PersonResponseDto.class);
    }

    @PostMapping()
    public PersonResponseDto createPerson(@Valid @RequestBody PersonCreateRequestDto personCreateRequestDto) {
        Person person = personService.createPerson(personCreateRequestDto);

        return modelMapper.map(person, PersonResponseDto.class);
    }

    @PutMapping()
    public PersonResponseDto updatePerson(@Valid @RequestBody PersonUpdateRequestDto personUpdateRequestDto) {
        Person person = personService.updatePerson(personUpdateRequestDto);

        return modelMapper.map(person, PersonResponseDto.class);
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable Long id) {
        personService.deletePersonById(id);
    }

    @GetMapping(value = "/search")
    public List<PersonResponseDto> searchPersons(@RequestParam(name = "name") String name, @RequestParam(name = "age") Integer age) {
        List<Person> persons = personService.searchPersons(name, age);

        return persons.stream()
                .map(person -> modelMapper.map(person, PersonResponseDto.class))
                .collect(Collectors.toList());
    }
}
