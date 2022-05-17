package com.andrijanica.SpringTest1.model.dto.request;

import com.andrijanica.SpringTest1.model.entity.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * DTO for {@link Person} create request.
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PersonCreateRequestDto {
    @NotEmpty
    private String name;

    @NotNull
    private Integer age;
}