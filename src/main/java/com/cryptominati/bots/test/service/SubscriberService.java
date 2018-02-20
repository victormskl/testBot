package com.cryptominati.bots.test.service;

import com.cryptominati.bots.test.entity.Subscriber;
import com.cryptominati.bots.test.entity.SubscruberState;
import org.telegram.telegrambots.api.objects.User;

public interface SubscriberService {
    Subscriber getSubscriber(int id);

    void putSubscriber(User user);

    void setState(int id, SubscruberState state);

    SubscruberState getState(int id);

    boolean contains(int id);
}
