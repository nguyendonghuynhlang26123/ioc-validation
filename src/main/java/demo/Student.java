package demo;

import annotations.Length;
import annotations.NotEmpty;

public class Student {
    @NotEmpty
    String name;

    @NotEmpty
    @Length(max = 4)
    String email;

    @NotEmpty
    int number;

    public Student(String name, String email) {
        this.name = name;
        this.email = email;
        number = 100;
    }
}
