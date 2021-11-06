package com.example.queue;

public class CommonTestMethods {

    public Long ejectId(String source) {
        int index = source.indexOf("=");
        int index2 = source.indexOf(",");
        return Long.parseLong(source.substring(index + 1, index2));
    }
}
