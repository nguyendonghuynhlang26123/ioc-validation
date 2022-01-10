import builder.impl.BaseValidateHolderBuilder;
import demo.pojos.*;
import handler.ViolationHandler;
import handler.impl.LoggingViolation;
import utils.exceptions.ChainBuilderException;
import utils.exceptions.ValidationException;
import validator.ValidatorHolder;
import validator.impl.BaseValidatorHolder;
import validator.impl.ValidationProvider;

import java.util.Collection;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        //Register handler
        ValidationProvider.getInstance().registerViolationListener(new LoggingViolation());

        /// Use case 1: Validate a VALIDATABLE class
        System.out.println("\nUse case 1: Validate a VALIDATABLE class");
        System.out.println("a: a plain simple VALIDATABLE class");
        Student s1 = new Student("Long", "", 4);
        s1.validate();

        System.out.println("b: Nested Validatable: Validate nested.student");
        Nested nested = new Nested(10);
        nested.setStudent(s1);
        nested.validate();

        System.out.println("c: Nested Collection: NestedCollection{ Collection<Student> students)");
        NestCollection nestCollection = new NestCollection();
        nestCollection.setStudents(List.of(s1, s1));
        nested.validate();
        System.out.println("-----");

        /// Use case 2: Validate using ValidatorBuilder
        // a: @Length(max=6)String
        System.out.println("\nUse case 2: Validate using ValidatorBuilder");
        System.out.println("a: Validate @Length(max=6)String ");
        String testString = "DADA5213FG";
        ValidationProvider.getInstance().<String>createValidatorBuilder()
                .applyConstraint()
                .length(6)
                .buildValidatable().validate(testString);

        /// b: @Max(4)int
        System.out.println("b: Validate @Max(4)int ");
        int a = 10;
        ValidationProvider.getInstance().<Integer>createValidatorBuilder()
                .applyConstraint()
                .max(4)
                .buildValidatable().validate(a);

        /// c: add validator to object fields
        System.out.println("c: Add validator to object fields Student{@Length(min=6)String name, @NotEmpty()String email, @Max(18)int age }");
        NoAnnoStudent noAnnoStudent = new NoAnnoStudent("Long","",4);
        ValidationProvider.getInstance().<NoAnnoStudent>createValidatorBuilder()
                .applyPojoConstraint()
                    .applyConstraint("name").length(6)
                    .applyConstraint("email").notEmpty()
                    .applyConstraint("age").max(18)
                .buildValidatable().validate(noAnnoStudent);

        /// d: Build validator for Collections:
        System.out.println("d: Build validator for Collections<@Length(min=3, max=6)String>");
        Collection<String> strings = List.of( "ab", "abc", "abcd", "abcde", "abcdef");
        ValidationProvider.getInstance()
                .<Collection<String>>createValidatorBuilder()
                .applyCollectionInternalConstraint()
                    .applyConstraint().length(3,6)
                .buildValidatable().validate(strings);
        System.out.println("-----");

    }
}
