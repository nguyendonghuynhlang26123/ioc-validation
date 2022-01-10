package demo.pojos;

import annotations.Ignore;
import annotations.Max;
import annotations.NotNull;
import validator.Validatable;

import java.util.List;

public class Nested implements Validatable {
    @NotNull
    @Ignore
    private Student student;

    @Max(4)
    int value;

    public Nested(int value) {
        this.value = value;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

}
