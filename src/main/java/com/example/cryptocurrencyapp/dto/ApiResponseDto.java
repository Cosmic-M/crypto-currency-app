package com.example.cryptocurrencyapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ApiResponseDto implements Serializable {
    private String asset_id_base;
    private ApiCryptoDto[] rates;
}
