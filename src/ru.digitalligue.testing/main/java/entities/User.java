package entities;

public class User {
    private final String name;
    private final String email;

    public User( String name, String email) {
        this.name = name;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Имя: " + name + ", email: " + email;
    }
}
