package com.spring.mvc.entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    private Integer id;

    @NotEmpty(message = "Name should be not empty")
    @Size(min = 2, max = 30, message = "The name must be between 2 and 30 characters long.")
    private String name;
    
    @Min(value = 0, message = "Age should be greater than 0")
    private Integer age;

    @NotEmpty(message = "Email should be not empty")
    @Email(message = "Email should be valid")
    private String email;

}
