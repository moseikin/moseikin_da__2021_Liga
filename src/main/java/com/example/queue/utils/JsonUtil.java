package com.example.queue.utils;

import com.example.queue.entities.requests.AuthRequest;
import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class JsonUtil {

    public AuthRequest getAuthRequest(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        Reader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName(StandardCharsets.UTF_8.name())));
        int c = 0;
        while ((c = reader.read()) != -1) {
            stringBuilder.append((char) c);
        }
        return new Gson().fromJson(String.valueOf(stringBuilder), AuthRequest.class);
    }
}
