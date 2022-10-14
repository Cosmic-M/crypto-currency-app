package com.example.cryptocurrencyapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiAssetDto {
    private String asset_id;
    private int type_is_crypto;
    private String name;
}
