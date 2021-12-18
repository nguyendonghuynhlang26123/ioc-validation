import demo.DemoCodeValidation;
import demo.Student;

public class Main {
    public static void main(String[] args) {
        Student s1 = new Student("Long", "");
        Student s2 = new Student("", "");
        Student s3 = new Student("test", "test@test");

        s1.validate();
        s2.validate();
        s3.validate();
//        ValidationProvider validationProvider = ValidationProvider.getInstance();
//        validationProvider.validate(s1);
//        validationProvider.validate(s2);
//        validationProvider.validate(s3);

        /// Validator builder
        var demo =new DemoCodeValidation();
        String testString1 = "";
        String testString2 = "dac";
        String testString3 = "dagdsacdafa";

        demo.demo(testString1);
        demo.demo(testString2);
        demo.demo(testString3);
    }
}
