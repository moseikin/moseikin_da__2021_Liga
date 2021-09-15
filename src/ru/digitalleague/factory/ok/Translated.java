package ru.digitalleague.factory.ok;

import ru.digitalleague.factory.ok.notification.decorator.SimpleUser;
import ru.digitalleague.factory.ok.notification.decorator.UserWithEmail;
import ru.digitalleague.factory.ok.notification.decorator.UserWithPhone;

import java.util.HashMap;
import java.util.Map;

public class Translated {
    private final Map<String, String> greetingMap = new HashMap<>();
    private final Map <String, String> gotSalaryMap = new HashMap<>();
    private final Map <String, String> nothingMap = new HashMap<>();
    private final String sampleName;
    private final User user;

    public Translated(String sampleName, User user) {
        this.sampleName = sampleName;
        this.user = user;
    }

    public String chooseNotification() {
        String language = user.getLocale().toString();
        String notification = "";

        if (sampleName.equals("greeting")) notification = initGreetingMap(language);
        if (sampleName.equals("gotSalary")) notification = initGotSalaryMap(language);
        if (sampleName.equals("nothing")) notification = initNothingMap(language);
        return notification;
    }

    private String initGreetingMap(String language){            // name
        greetingMap.put("ru_RU", "Уважаемый, %s! \n С добрым утром!");
        greetingMap.put("en", "Dear, %s! \n Good morning!");
        greetingMap.put("zh_CN", "親, %s! \n 早上好！");
        return new SimpleUser(greetingMap.get(language), user).getText();
    }

    private String initGotSalaryMap(String language){               // name, email
        gotSalaryMap.put("ru_RU", "%s, на вашу электронную почту: %s зачислена зарплата");
        gotSalaryMap.put("en", "%s, your %s received salary");
        gotSalaryMap.put("zh_CN", "%s, 工資已記入 %s 您的郵箱");
        return new UserWithEmail(new SimpleUser(gotSalaryMap.get(language), user)).getText();
    }

    private String initNothingMap(String language){             // name, phone
        nothingMap.put("ru_RU", "%s, это ниочемное сообщение на ваш телефон: %s");
        nothingMap.put("en", "%s, this is stupid message on your phone: %s");
        nothingMap.put("zh_CN", "%s, 這不是給您地址的消息: %s");
        return new UserWithPhone(new SimpleUser(nothingMap.get(language), user)).getText();
    }
}
