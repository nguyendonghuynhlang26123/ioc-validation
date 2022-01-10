package demo.pojos;

public class NoAnnoStudent {
    String name;
    String email;
    int age;
    NestCollection nest;

    public NoAnnoStudent(String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public void setNest(NestCollection nest) {
        this.nest = nest;
    }
}
