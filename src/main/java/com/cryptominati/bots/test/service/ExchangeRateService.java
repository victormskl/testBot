package com.cryptominati.bots.test.service;

public interface ExchangeRateService {
    String getExchangeRate(String currencyId);
    String convert(double value, String fromCurrencyId, String toCurrencyId);
}
