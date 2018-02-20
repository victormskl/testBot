package com.cryptominati.bots.test;

import com.cryptominati.bots.test.controller.BotController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class Bot extends TelegramLongPollingBot {
    @Autowired
    private BotController botController;

    private Map<Long, String> images = new HashMap<>();

    static {
        ApiContextInitializer.init();
    }

    @PostConstruct
    private void Init() {
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {
            botsApi.registerBot(this);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        System.out.println("PhotoBot successfully started!");
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            try {
                execute(botController.textMessage(update.getMessage())); // Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (update.hasMessage() && update.getMessage().hasPhoto()) {
            try {
                sendPhoto(botController.photoMessage(update.getMessage())); // Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        // Return bot username
        // If bot username is @com.cryptominati.bots.Bot, it must return 'com.cryptominati.bots.Bot'
        return "longPollingTestbot";
    }

    @Override
    public String getBotToken() {
        // Return bot token from BotFather
        return "544137171:AAEizBwOdomMgOgNXfuw1nntyTKId5f8Yh8";
    }
}