package demo.pojos;

import annotations.*;
import validator.Validatable;

import java.time.LocalDate;

public class Student implements Validatable {
    @MatchPattern(pattern = "^[a-zA-Z\\s]+$", message = "Name should be match the characters")
    @NotEmpty(message = "Name should not be empty")
    String name;

    @Email
    @NotEmpty(message = "Email should not be empty")
    @Length(max = 4, message = "Email length should has less than 4 characters")
    String email;

    @Max(value = 18, message = "Age must < 18")
    long age;

    @DateLimit(start = "01/01/2000", end = "31/12/2024")
    LocalDate birth;

    public Student(String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }
}
