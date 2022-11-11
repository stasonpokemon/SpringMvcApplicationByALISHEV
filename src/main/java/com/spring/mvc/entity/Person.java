package com.spring.mvc.entity;

import javax.persistence.*;
import javax.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name should be not empty")
    @Size(min = 2, max = 30, message = "The name must be between 2 and 30 characters long.")
    private String name;

    @Min(value = 0, message = "Age should be greater than 0")
    private Integer age;

    @NotEmpty(message = "Email should be not empty")
    @Email(message = "Email should be valid")
    private String email;

    // Country, City, Postal code(6 digits)
    // Belarus, Vitebsk, 210001
    @Pattern(regexp = "[A-Z]\\w+, [A-Z]\\w+, \\d{6}", message = "Your address should be in this format: Country, City, Postal Code (6 digits)")
    private String address;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_of_birth")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateOfBirthday;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_at")
    private Date createAt;

}
