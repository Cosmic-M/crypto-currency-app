package com.example.cryptocurrencyapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class CryptoResponseDto {
    private LocalDateTime time;
    private String asset_id_quote;
    private String rate;
}
