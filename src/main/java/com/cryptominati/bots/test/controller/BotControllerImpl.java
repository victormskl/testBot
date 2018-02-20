package com.cryptominati.bots.test.controller;

import com.cryptominati.bots.test.entity.Subscriber;
import com.cryptominati.bots.test.entity.SubscruberState;
import com.cryptominati.bots.test.service.MessageService;
import com.cryptominati.bots.test.service.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BotControllerImpl implements BotController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private SubscriberService subscruberService;
    private ReplyKeyboardMarkup keyboardMainMenu;
    private ReplyKeyboardMarkup keyboardConvertMenu;
    private Map<String, Method> menuMap;

    @PostConstruct
    private void init() {
        initKeyboards();
        menuMap = new HashMap<>();
        for (Method method : this.getClass().getDeclaredMethods()) {
            BotMethod botMethod = method.getAnnotation(BotMethod.class);
            if (botMethod != null) {
                menuMap.put(method.getName(), method);
            }
        }
    }

    private void initKeyboards() {
        keyboardMainMenu = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add(new KeyboardButton("/convert"));
        row.add(new KeyboardButton("/info"));
        keyboard.add(row);
        keyboardMainMenu.setKeyboard(keyboard);

        keyboardConvertMenu = new ReplyKeyboardMarkup();
        keyboard = new ArrayList<>();
        row = new KeyboardRow();
        row.add(new KeyboardButton("/main"));
        keyboard.add(row);
        keyboardConvertMenu.setKeyboard(keyboard);
    }

    public SendMessage textMessage(Message message) {
        String command = message.getText();
        SendMessage resultMessage = new SendMessage().setText("");
        subscruberService.putSubscriber(message.getFrom());
        if (command.startsWith("/")) {
            command = command.substring(1);
            if (menuMap.containsKey(command)) {
                try {
                    resultMessage = (SendMessage) menuMap.get(command).invoke(this, message);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    resultMessage = messageService.getExchangeRateMessage(command);
                } catch (Exception e) {
                    resultMessage = error(message);
                }
            }
        } else {
            switch (subscruberService.getState(message.getFrom().getId())){
                case MAIN:
                    resultMessage = error(message);
                case CONVERTER:
                    resultMessage.setText("Функционал пока не реализован..");
                    break;
            }
        }
        resultMessage.setChatId(message.getChatId());
        return resultMessage;
    }

    @BotMethod
    private SendMessage start(Message message) {
        return main(message).setText("Здорова, " + message.getFrom().getFirstName() + "!");
    }

    @BotMethod
    private SendMessage error(Message message) {
        return main(message).setText("Введи нормальную команду!");
    }

    @BotMethod
    private SendMessage main(Message message) {
        subscruberService.putSubscriber(message.getFrom());
        subscruberService.setState(message.getFrom().getId(), SubscruberState.MAIN);
        SendMessage resultMessage;
        resultMessage = new SendMessage()
                .setText("Главное меню")
                .setReplyMarkup(keyboardMainMenu);
        return resultMessage;
    }

    @BotMethod
    private SendMessage info(Message message) {
        return new SendMessage()
                .setText("Описание всякой херни");
    }

    @BotMethod
    private SendMessage convert(Message message) {
        subscruberService.putSubscriber(message.getFrom());
        subscruberService.setState(message.getFrom().getId(), SubscruberState.CONVERTER);
        SendMessage resultMessage;
        resultMessage = new SendMessage()
                .setText("Введите запрос наподобии \"300 BTC to ETH\"")
                .setReplyMarkup(keyboardConvertMenu);
        return resultMessage;
    }

    public SendPhoto photoMessage(Message message) {
        return new SendPhoto()
                .setCaption("Функционал пока не реализован..")
                .setPhoto(message.getPhoto().get(0).getFileId())
                .setChatId(message.getChatId());
    }
}
