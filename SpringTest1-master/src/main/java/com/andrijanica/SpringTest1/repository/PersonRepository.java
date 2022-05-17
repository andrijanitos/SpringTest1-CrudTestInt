package com.andrijanica.SpringTest1.repository;

import com.andrijanica.SpringTest1.model.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Represents {@link Person} repository.
 */
public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByNameAndAge(String name, Integer age);
}
