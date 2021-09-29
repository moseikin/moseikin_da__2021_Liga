package ru.digitalleague.factory.ok.templates;

import java.util.ArrayList;
import java.util.List;

public enum Templates {
    LANGUAGES("Русский", "English"),
    HAPPY_BIRTHDAY("С днем рождения", "Happy birthday"),
    GOOD_MORNING("С добрым утром", "Good morning"),
    BON_APPETIT("Приятного аппетита", "Bon appetit");

    private final List<String> locales = new ArrayList<>();

    Templates(String russian, String english) {
        locales.add(russian);
        locales.add(english);

    }

    public List<String> getLocales() {
        return locales;
    }
}
