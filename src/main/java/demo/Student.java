package demo;

import annotations.Length;
import annotations.NotEmpty;
import validator.Validatable;

public class Student implements Validatable {
    @NotEmpty
    String name;

    @NotEmpty
    @Length(max = 4)
    String email;

    int number;

    public Student(String name, String email) {
        this.name = name;
        this.email = email;
        number = 100;
    }
}
