package demo;

import annotations.Length;
import annotations.Max;
import annotations.NotEmpty;
import validator.Validatable;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Student implements Validatable {
    @NotEmpty
    String name;

    @NotEmpty
    @Length(max = 4)
    String email;

    @Max(18)
    BigDecimal number;

    public Student(String name, String email) {
        this.name = name;
        this.email = email;
        number = BigDecimal.valueOf(18.4);
    }
}
