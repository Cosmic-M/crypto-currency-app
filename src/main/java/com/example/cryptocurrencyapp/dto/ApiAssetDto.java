package com.example.cryptocurrencyapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiAssetDto {
    @JsonProperty("asset_id")
    private String assetId;
    @JsonProperty("type_is_crypto")
    private int cryptoType;
    private String name;
}
