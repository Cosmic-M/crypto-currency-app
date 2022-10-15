package com.example.cryptocurrencyapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageResponseDto {
    private String timeExchange;
    private String symbolId;
    private String priceUsd;
}
