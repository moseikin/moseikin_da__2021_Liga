package ru.digitalleague.factory.ok;

import java.util.Random;

public class RandomSample {

    public String getRandomSample() {
        return Samples.values()[new Random().nextInt(Samples.values().length)].toString();
    }
}
