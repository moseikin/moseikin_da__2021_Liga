package com.example;

import com.example.service.FriendsService;
import com.example.service.SchoolService;
import com.example.service.UserPostsService;
import com.example.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        SchoolService.addSchool();
        UserService.addUsers();     // нет уникальных юзеров по имени или фамилии
        UserPostsService.addPost();     // новая запись первого юзера
        SchoolService.allUsrInSchool();  // все школьники по школам
        UserService.getAllUsers();      // все пользователи
        FriendsService.addFriendRequest();     // запрос в друзья
        FriendsService.acceptRequest();     // принять запрос
        FriendsService.allFriends();        // все друзья пользователя
        FriendsService.allIncomingRequests(); // входяшие заявки пользователя
        FriendsService.allOutgoingRequests(); // исходящие заявки

//        FriendsService.rejectRequest();   // отклонить запрос или удалить из друзей

//        UserService.deleteUser();

//        UserService.deleteAllUsers();


    }
}
