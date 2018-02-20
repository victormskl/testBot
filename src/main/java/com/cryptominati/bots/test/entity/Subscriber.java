package com.cryptominati.bots.test.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.api.objects.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subscriber {
    int id;
    SubscruberState state;
    private String firstName;
    private boolean isBot;
    private String lastName;
    private String userName;
    private String languageCode;

    public Subscriber(int id, SubscruberState state){
        this.id = id;
        this.state = state;
    }

    public void update(User user){
        firstName = user.getFirstName();
        isBot = user.getBot();
        lastName = user.getLastName();
        userName = user.getUserName();
        languageCode = user.getLanguageCode();
    }
}
