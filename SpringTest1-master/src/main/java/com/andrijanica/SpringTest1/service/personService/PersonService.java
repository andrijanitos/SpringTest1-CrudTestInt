package com.andrijanica.SpringTest1.service.personService;

import com.andrijanica.SpringTest1.model.dto.request.PersonCreateRequestDto;
import com.andrijanica.SpringTest1.model.dto.request.PersonUpdateRequestDto;
import com.andrijanica.SpringTest1.model.entity.Person;

import java.util.List;

/**
 * Service for {@link Person} operations.
 */
public interface PersonService {

    /**
     * Gets all persons.
     *
     * @return List of all persons.
     */
    List<Person> getAllPersons();

    /**
     * Gets person by id.
     *
     * @param id Id.
     * @return Person is exists, otherwise throws ResourceNotFoundException.
     */
    Person getPersonById(Long id);

    /**
     * Creates new person.
     *
     * @param personCreateRequestDto Person create request DTO
     * @return Created person.
     */
    Person createPerson(PersonCreateRequestDto personCreateRequestDto);

    /**
     * Updates person.
     *
     * @param personUpdateRequestDto Person update request DTO.
     * @return Updated person.
     */
    Person updatePerson(PersonUpdateRequestDto personUpdateRequestDto);

    /**
     * Deletes person.
     *
     * @param id Id.
     */
    void deletePersonById(Long id);

    /**
     * Searches persons by name.
     *
     * @param name Name.
     * @param age Age.
     * @return Persons founded by provided name.
     */
    List<Person> searchPersons(String name, Integer age);
}
