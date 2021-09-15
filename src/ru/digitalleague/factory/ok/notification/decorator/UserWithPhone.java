package ru.digitalleague.factory.ok.notification.decorator;

public class UserWithPhone implements DecoratorInterface {

    private final SimpleUser simpleUser;

    public UserWithPhone(SimpleUser simpleUser) {
        this.simpleUser = simpleUser;
    }

    @Override
    public String getText() {
        return String.format(simpleUser.getNotification(), simpleUser.getUser().getName(), simpleUser.getUser().getPhone());
    }
}
