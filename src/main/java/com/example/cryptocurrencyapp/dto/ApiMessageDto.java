package com.example.cryptocurrencyapp.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApiMessageDto {
    private String time_exchange;
    private String symbol_id;
    private String price;
}
