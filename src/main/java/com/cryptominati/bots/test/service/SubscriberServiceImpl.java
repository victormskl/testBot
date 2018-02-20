package com.cryptominati.bots.test.service;

import com.cryptominati.bots.test.entity.Subscriber;
import com.cryptominati.bots.test.entity.SubscruberState;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.objects.User;

import java.util.HashMap;
import java.util.Map;

@Service
public class SubscriberServiceImpl implements SubscriberService {
    private Map<Integer, Subscriber> subscriberMap = new HashMap<>();

    @Override
    public Subscriber getSubscriber(int id) {
        return subscriberMap.get(id);
    }

    @Override
    public void putSubscriber(User user) {
        Subscriber subscriber = subscriberMap.get(user.getId());
        if (subscriber == null){
            subscriber = new Subscriber(user.getId(), SubscruberState.MAIN);
        }
        subscriber.update(user);
        subscriberMap.put(subscriber.getId(), subscriber);
    }

    @Override
    public void setState(int id, SubscruberState state) {
        subscriberMap.get(id).setState(state);
    }

    @Override
    public SubscruberState getState(int id) {
        return subscriberMap.get(id).getState();
    }

    @Override
    public boolean contains(int id) {
        return subscriberMap.containsKey(id);
    }
}
