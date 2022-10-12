package com.example.cryptocurrencyapp.service;

import com.example.cryptocurrencyapp.dto.CryptoResponseDto;
import com.example.cryptocurrencyapp.model.Crypto;
import org.springframework.stereotype.Component;

@Component
public class CryptoMapper {
    public CryptoResponseDto toDto(Crypto crypto) {
        CryptoResponseDto responseDto = new CryptoResponseDto();
        responseDto.setTime(crypto.getTime());
        responseDto.setAsset_id_quote(crypto.getAssetIdQuote());
        responseDto.setRate(crypto.getRate());
        return responseDto;
    }
}
