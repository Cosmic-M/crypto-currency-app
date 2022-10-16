package com.example.cryptocurrencyapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApiMessageDto {
    @JsonProperty("time_exchange")
    private String timeExchange;
    @JsonProperty("symbol_id")
    private String symbolId;
    private String price;
}
