package demo.pojos;

import annotations.Length;
import annotations.Max;
import annotations.NotEmpty;
import validator.Validatable;

public class Student implements Validatable {
    @NotEmpty(message = "Name should not be empty")
    String name;

    @NotEmpty(message = "Email should not be empty")
    @Length(max = 4, message = "Email length should has less than 4 characters")
    String email;

    @Max(value = 18, message = "Age must < 18")
    long age;

    public Student(String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }
}
