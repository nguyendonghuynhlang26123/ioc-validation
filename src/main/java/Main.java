import annotations.Length;
import builder.ValidatorBuilder;
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

        /// Validator builder
        ValidatorBuilder validatorBuilder = new ValidatorBuilder<String>(String.class).notEmpty().length(2,5);
        String testString1 = "";
        String testString2 = "dac";
        String testString3 = "dagdsacdafa";

        System.out.println("test1: " + validatorBuilder.build().validate(testString1));
        System.out.println("test2: " + validatorBuilder.build().validate(testString2));
        System.out.println("test3: " + validatorBuilder.build().validate(testString3));
    }
}
