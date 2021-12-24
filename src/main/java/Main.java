import demo.DemoCodeValidation;
import demo.Student;
import handler.impl.LoggingViolation;
import validator.impl.ValidationProvider;

public class Main {
    public static void main(String[] args) {
        Student s1 = new Student("Long", "", 4);
        Student s2 = new Student("", "",20);
        Student s3 = new Student("test", "test@test", 28);

        ValidationProvider validationProvider = ValidationProvider.getInstance();
        validationProvider.registerViolationListener(new LoggingViolation());
        s1.validate();
        System.out.println("-----");
        s2.validate();
        System.out.println("-----");
        s3.validate();
        System.out.println("-----");

        /// Validator builder
        var demo =new DemoCodeValidation();
        String testString1 = "";
        String testString2 = "dac";
        String testString3 = "dagdsacdafa";
//
        demo.demo(testString1);
        System.out.println("-----");
        demo.demo(testString2);
        System.out.println("-----");
        demo.demo(testString3);
        System.out.println("-----");
    }
}
