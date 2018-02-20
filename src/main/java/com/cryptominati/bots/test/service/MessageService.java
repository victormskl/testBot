package com.cryptominati.bots.test.service;

import org.telegram.telegrambots.api.methods.send.SendMessage;


public interface MessageService {
    public SendMessage getErrorMessage();

    public SendMessage getErrorMessage(String errorText);

    SendMessage getExchangeRateMessage(String currencyId);

    SendMessage getConvertMessage(double value, String fromCurrencyId, String toCurrencyId);
}
