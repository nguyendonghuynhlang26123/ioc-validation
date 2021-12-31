import demo.pojos.*;
import handler.impl.LoggingViolation;
import utils.exceptions.ChainBuilderException;
import utils.exceptions.ValidationException;
import validator.ValidatorHolder;
import validator.impl.ValidationProvider;

import java.util.Collection;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Student s1 = new Student("Long", "", 4);
        Student s2 = new Student("", "",20);
        Student s3 = new Student("test", "test@test", 28);
        NoAnnoStudent noAnnoStudent = new NoAnnoStudent("","",20);
        NoAnnoStudent noAnnoStudent1 = new NoAnnoStudent("Long","",4);
        NoAnnoStudent noAnnoStudent2 = new NoAnnoStudent("test", "test@test", 28);
        Nested nested = new Nested();
        NestCollection nestCollection = new NestCollection();

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
        System.out.println("-----");
        nestCollection.setStudents(List.of(s1, s2, s3));
//        nestCollection.validate();
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
//        Data data = new Data();
//        data.validate();
//        System.out.println("-----");
//        data.setStudents(Arrays.asList(s1, s2, s3));
//        data.validate();

//        compositeBuilder(noAnnoStudent);

//        collectionPrimitiveBuilder(List.of("sa","bada","asdadasdasd"));

//        collectionPojoBuilder(List.of(noAnnoStudent,noAnnoStudent1,noAnnoStudent2));
        noAnnoStudent.setNest(nestCollection);
//        objectContainNestCollectionBuilder(noAnnoStudent);
        collectionObjectContainNestCollectionBuilder(List.of(noAnnoStudent, noAnnoStudent, noAnnoStudent));
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
                    .applyPojoConstraints()
                        .applyConstraint("name").notEmpty()
                        .applyConstraint("email").notEmpty().length(4)
                        .applyConstraint("age").max(18)
                    .endPojoConstraint()
                    .buildValidatable();
            holder.validate(object);
        } catch (ValidationException| ChainBuilderException e){
            e.printStackTrace();
        }
    }

    public static void collectionPrimitiveBuilder(Collection<String> values){
        try{
            ValidatorHolder<Collection<String>> validatable = ValidationProvider.getInstance()
                    .<Collection<String>>createValidatorBuilder()
                    .applyCollectionConstraints()
                    .applyConstraint()
                    .length(3,6)
                    .buildValidatable();
            validatable.validate(values);
        } catch (ValidationException| ChainBuilderException e){
            // TODO: catch errors here???
            e.printStackTrace();
        }
    }

    public static void collectionPojoBuilder(Collection<NoAnnoStudent> students){
        try{
            ValidatorHolder<Collection<NoAnnoStudent>> holder = ValidationProvider.getInstance()
                    .<Collection<NoAnnoStudent>>createValidatorBuilder()
                    .applyCollectionConstraints()
                    .applyPojoConstraints()
                    .applyConstraint("name").notEmpty()
                    .applyConstraint("email").notEmpty().length(4)
                    .applyConstraint("age").max(18)
                    .buildValidatable();
            holder.validate(students);
        } catch (ValidationException| ChainBuilderException e){
            e.printStackTrace();
        }
    }

    public static void objectContainNestCollectionBuilder(NoAnnoStudent noAnnoStudent){
        try{
            var holder = ValidationProvider.getInstance()
                    .<NoAnnoStudent>createValidatorBuilder()
                    .applyPojoConstraints()
                        .applyConstraint("name").notEmpty()
                        .applyConstraint("age").max(18)
                        .applyPojoConstraints("nest")
                            .applyCollectionConstraints("students")
                                .applyPojoConstraints()
                                    .applyConstraint("name").notEmpty()
                                    .applyConstraint("email").notEmpty().length(6)
                                    .applyConstraint("age").max(6)
                                .endPojoConstraint()
                        .endPojoConstraint()
                        .applyConstraint("email").notEmpty().length(6)
                    .buildValidatable();
            holder.validate(noAnnoStudent);
        } catch (ChainBuilderException e){
            e.printStackTrace();
        }
    }

    public static void collectionObjectContainNestCollectionBuilder(Collection<NoAnnoStudent> objects){
        try{
            var holder = ValidationProvider.getInstance()
                    .<Collection<NoAnnoStudent>>createValidatorBuilder()
                    .applyCollectionConstraints()
                        .applyPojoConstraints()
                            .applyConstraint("name").notEmpty()
                            .applyConstraint("age").max(18)
                            .applyPojoConstraints("nest")
                                .applyCollectionConstraints("students")
                                    .applyPojoConstraints()
                                        .applyConstraint("name").notEmpty()
                                        .applyConstraint("email").notEmpty().length(6)
                                        .applyConstraint("age").max(6)
                                    .endPojoConstraint()
                            .endPojoConstraint()
                            .applyConstraint("email").notEmpty().length(6)
                    .buildValidatable();
            holder.validate(objects);
        } catch (ChainBuilderException e){
            e.printStackTrace();
        }
    }
}
