package com.example;

import com.example.entities.Address;
import com.example.entities.People;
import com.example.entities.School;
import com.example.repository.PeopleRepository;
import com.example.repository.service.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);

        BasicService.addSchool();
//       BasicService.addPeople();

    }
}
