package violation;

//TODO: Using builder to build violations
public class Violation {
    Object object;
    String message;

    public Violation(Object object, String message) {
        this.object = object;
        this.message = message;
    }

    @Override
    public String toString() {
        return "Violation{" +
                "class=" + object.getClass().getSimpleName() +
                ", message=" + message +
                '\'' +
                '}';
    }
}
