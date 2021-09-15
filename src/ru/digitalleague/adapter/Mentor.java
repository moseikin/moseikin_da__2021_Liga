package ru.digitalleague.adapter;

public class Mentor {
    private Long id;
    private String name;

    public Mentor(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
