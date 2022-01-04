package demo.pojos;

import annotations.Length;
import demo.customized.ArrayNotEmpty;
import validator.Validatable;

import java.util.List;

public class NestCollection implements Validatable {
    public NestCollection(){}

    private List<Student> students;

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
