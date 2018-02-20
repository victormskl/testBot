package com.cryptominati.bots.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private ExchangeRateService coinmarketcapService;

    public SendMessage getErrorMessage(){
       return getErrorMessage("Unknown commabd!");
    }

    public SendMessage getErrorMessage(String errorText){
        return new SendMessage().setText("Error: " + errorText);
    }


    @Override
    public SendMessage getExchangeRateMessage(String currencyId) {
        return new SendMessage().setText(coinmarketcapService.getExchangeRate(currencyId));
    }

    @Override
    public SendMessage getConvertMessage(double value, String fromCurrencyId, String toCurrencyId) {
        return null;
    }
}
