package ru.digitalleague.factory.ok;

import java.util.Locale;

public class User {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private Locale locale;

    public User(Long id, String name, String email, String phone, Locale locale) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.locale = locale;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Locale getLocale() {
        return locale;
    }
}
