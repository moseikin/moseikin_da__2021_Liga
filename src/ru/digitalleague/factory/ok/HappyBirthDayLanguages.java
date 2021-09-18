package ru.digitalleague.factory.ok;

public enum HappyBirthDayLanguages {
    DEFAULT ("Уважаемый, ", "поздравляем c днем рождения"),
    ENGLISH("Dear, ", "happy birthday"),
    CHINESE("親, ", "生日快樂");


    HappyBirthDayLanguages(String dear, String happyBirthDay) {
        this.dear = dear;
        this.happyBirthDay = happyBirthDay;
    }

    private final String dear;
    private final String happyBirthDay;

    public String getDear(){
        return dear;
    }

    public String getHappyBirthDay() {
        return happyBirthDay;
    }
}
