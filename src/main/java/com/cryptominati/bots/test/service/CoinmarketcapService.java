package com.cryptominati.bots.test.service;

import com.cryptominati.bots.test.dao.ExchangeRateDao;
import com.cryptominati.bots.test.entity.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoinmarketcapService implements ExchangeRateService {
    @Autowired
    ExchangeRateDao exchangeRateDao;

    @Override
    public String getExchangeRate(String currencyId) {
        Currency currency =  exchangeRateDao.getExchangeRate(currencyId);
        return currencyId + " = " + String.format("%f",currency.getPrice_usd()) + " USD = "
        + String.format("%8f",currency.getPrice_btc()) + " BTC";
    }

    @Override
    public String convert(double value, String fromCurrencyId, String toCurrencyId) {
        return null;
    }
}
