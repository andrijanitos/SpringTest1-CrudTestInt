package com.andrijanica.SpringTest1.model.dto.response;

import com.andrijanica.SpringTest1.model.entity.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * DTO for {@link Person} response.
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PersonResponseDto {
    private Long id;
    private String name;
    private Integer age;
}
