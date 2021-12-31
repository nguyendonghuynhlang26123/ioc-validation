package demo.pojos;

import validator.Validatable;

import java.util.List;

public class NestCollection implements Validatable {
    public NestCollection(){}

    private List<Student> students;

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
