import annotations.Length;
import demo.Student;

public class Main {
    public static void main(String[] args) {
        Student s1 = new Student("Long", "");
        Student s2 = new Student("", "");
        Student s3 = new Student("test", "test@test");

        ValidationProvider validationProvider = ValidationProvider.getInstance();
        System.out.println("s1: " + validationProvider.validate(s1));
        System.out.println("s2: " + validationProvider.validate(s2));
        System.out.println("s3: " + validationProvider.validate(s3));
    }
}
