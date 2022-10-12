package com.example.cryptocurrencyapp.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class CryptoResponseDto {
    private Long id;
    private LocalDateTime time;
    private String asset_id_quote;
    private String rate;
}
