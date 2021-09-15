package ru.digitalleague.factory.ok.notification.decorator;

public class UserWithEmail implements DecoratorInterface {
    private SimpleUser simpleUser;

    public UserWithEmail(SimpleUser simpleUser) {
        this.simpleUser = simpleUser;
    }

    @Override
    public String getText() {
        return String.format(simpleUser.getNotification(), simpleUser.getUser().getName(), simpleUser.getUser().getEmail());
    }
}
