package com.example.cryptocurrencyapp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiMessage {
    private String timeExchange;
    private String symbolId;
    private String price;
}
