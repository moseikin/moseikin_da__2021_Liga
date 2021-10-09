package com.example.service;


import com.example.dao.FriendsJpaDao;
import com.example.dao.UsersJpaDao;
import com.example.entities.Friends;
import com.example.entities.Usr;

import java.util.List;

public class FriendsService {
    private static final UsersJpaDao USERS_JPA_DAO = new UsersJpaDao();
    private static final FriendsJpaDao FRIENDS_JPA_DAO = new FriendsJpaDao();

    // заявка в друзья
    public static void addFriendRequest(){
        Usr usr = getUser();
        Usr usrToAdd = getUserToAdd();
        Friends friends = new Friends(new Friends.FriendsPk(usr.getId(), usrToAdd.getId()), false);
        FRIENDS_JPA_DAO.addNewRequest(friends);
    }

    // принять заявку
    public static void acceptRequest(){
        Usr usr = getUserToAdd();
        List<Friends> friendsList = FRIENDS_JPA_DAO.getAllIncomingRequests(usr);
        Friends friends = friendsList.get(0);
        friends.setAreFriends(true);
        FRIENDS_JPA_DAO.changeAreFriendsCondition(friends);
    }

    // отклонить заявку или удалить из друзей
    public static void rejectRequest(){
        Usr usr = getUserToAdd();
        List<Friends> friendsList = FRIENDS_JPA_DAO.getAllIncomingRequests(usr);
        Friends friends = friendsList.get(0);
        FRIENDS_JPA_DAO.rejectRequest(friends);
    }


    // исходящие заявки
    public static void allOutgoingRequests(){
        Usr usr = getUser();
        List<Friends> friendsList = FRIENDS_JPA_DAO.getAllOutgoingRequest(usr);
        for (Friends friends : friendsList){
            System.out.println(friends);
        };
    }

    // входящие заявки
    public static void allIncomingRequests(){
        Usr usr = getUserToAdd();
        List<Friends> friendsList = FRIENDS_JPA_DAO.getAllIncomingRequests(usr);
        for (Friends friends : friendsList) {
            System.out.println(friends);
        }
    }

    // не разделял по принятым или активным заявкам
    public static void allFriends(){
        Usr usr = getUser();
        List<Friends> friendsList = usr.getFriendsList();
        System.out.println("FRIENDS = " + friendsList.size());
        for (Friends friends : friendsList) {
            System.out.println(friends);
        }
    }

    private static Usr getUser() {
        return USERS_JPA_DAO.usrList().get(0); // ищу не по id, чтоб избежать NPE
    }

    private static Usr getUserToAdd() {
        return USERS_JPA_DAO.usrList().get(1);
    }
}
