package demo.pojos;

import annotations.NotNull;
import validator.Validatable;

public class Nested implements Validatable {
    @NotNull
    private Student student;

    public Nested(){}

    public void setStudent(Student student) {
        this.student = student;
    }
}
