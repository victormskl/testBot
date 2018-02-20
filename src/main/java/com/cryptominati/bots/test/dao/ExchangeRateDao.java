package com.cryptominati.bots.test.dao;

import com.cryptominati.bots.test.entity.Currency;

public interface ExchangeRateDao {
    Currency getExchangeRate(String currencyId);
}
