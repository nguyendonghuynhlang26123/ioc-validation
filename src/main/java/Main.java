import demo.pojos.Data;
import demo.pojos.Nested;
import demo.pojos.NoAnnoStudent;
import demo.pojos.Student;
import handler.impl.LoggingViolation;
import utils.exceptions.ChainBuilderException;
import utils.exceptions.ValidationException;
import validator.ValidatorHolder;
import validator.impl.ValidationProvider;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Student s1 = new Student("Long", "", 4);
        Student s2 = new Student("", "",20);
        Student s3 = new Student("test", "test@test", 28);
        NoAnnoStudent noAnnoStudent = new NoAnnoStudent("","",20);
        Nested nested = new Nested();

        /// POJO classes
        ValidationProvider validationProvider = ValidationProvider.getInstance();
        validationProvider.registerViolationListener(new LoggingViolation());
//        s1.validate();
//        System.out.println("-----");
//        s2.validate();
//        System.out.println("-----");
//        s3.validate();
//        System.out.println("-----");
//        nested.validate();
//        System.out.println("-----");
//        nested.setStudent(s2);
//        nested.validate();
//        System.out.println("-----");

        /// Primitive variables
//        String testString1 = "";
//        String testString2 = "dac";
//        String testString3 = "dagdsacdafa";
//        demo(testString1);
//        System.out.println("-----");
//        demo(testString2);
//        System.out.println("-----");
//        demo(testString3);
//        System.out.println("-----");

        /// Customized
        Data data = new Data();
        data.validate();
        System.out.println("-----");
        data.setStudents(Arrays.asList(s1, s2, s3));
        data.validate();

//        compositeBuilder(noAnnoStudent);
    }

    public static void demo(String input){
        try{
            ValidatorHolder<String> validatable = ValidationProvider.getInstance().<String>createValidatorBuilder()
                    .applyConstraint()
                    .length(6)
                    .buildValidatable();
            validatable.validate(input);
        } catch (ValidationException| ChainBuilderException e){
            // TODO: catch errors here???
            e.printStackTrace();
        }
    }

    public static void compositeBuilder(NoAnnoStudent object){
        try{
            ValidatorHolder<NoAnnoStudent> holder = ValidationProvider.getInstance().<NoAnnoStudent>createValidatorBuilder()
                    .handler(ValidationProvider.getInstance().getHandler())
                    .applyComplexConstraints()
                        .applyConstraint("name").notEmpty()
                        .applyConstraint("email").notEmpty().length(4)
                        .applyConstraint("age").max(18)
                    .buildValidatable();
            holder.validate(object);
        } catch (ValidationException| ChainBuilderException e){
            e.printStackTrace();
        }
    }
}
