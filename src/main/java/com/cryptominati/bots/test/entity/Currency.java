package com.cryptominati.bots.test.entity;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data

public class Currency {
    String id;
    String name;
    String symbol;
    int rank;
    double price_usd;
    double price_btc;
    @SerializedName("24h_volume_usd")
    double volume_usd_24h;
    double market_cap_usd;
    double available_supply;
    double total_supply;
    double max_supply;
    double percent_change_1h;
    double percent_change_24h;
    double percent_change_7d;
    double last_updated;
}
