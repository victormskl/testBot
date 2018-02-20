package com.cryptominati.bots.test.dao;

import com.cryptominati.bots.test.entity.Currency;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CoinmarketcapDao implements ExchangeRateDao {
    private String URL_GET_ALL_CURRENCY = "https://api.coinmarketcap.com/v1/ticker/?limit=0";

    private Map<String, Currency> currencyMap = new ConcurrentHashMap<>();

    @Override
    public Currency getExchangeRate(String currencyId) {
        return currencyMap.get(currencyId);
    }

    @Scheduled(fixedDelay = 20000)
    protected void updateExchangeRates(){
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(URL_GET_ALL_CURRENCY, String.class);
        Gson gson = new Gson();
        List<Currency> currencyList = gson.fromJson(result,new TypeToken<List<Currency>>(){}.getType());
        currencyList.forEach(currency -> currencyMap.put(currency.getSymbol(),currency));
    }
}
