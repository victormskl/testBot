package com.cryptominati.bots.test.controller;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Message;

public interface BotController {
    SendMessage textMessage(Message message);

    SendPhoto photoMessage(Message message);
}
