package com.example.cryptocurrencyapp.service;

import com.example.cryptocurrencyapp.dto.ApiAssetDto;
import com.example.cryptocurrencyapp.dto.AssetResponseDto;
import com.example.cryptocurrencyapp.model.Asset;
import org.springframework.stereotype.Component;

@Component
public class AssetMapper {
    public Asset toModel(ApiAssetDto apiAssetDto) {
        Asset asset = new Asset();
        asset.setAssetId(apiAssetDto.getAsset_id());
        asset.setTitle(apiAssetDto.getName());
        return asset;
    }

    public AssetResponseDto toDto(Asset asset) {
        AssetResponseDto responseDto = new AssetResponseDto();
        responseDto.setId(asset.getId());
        responseDto.setAssetId(asset.getAssetId());
        responseDto.setTitle(asset.getTitle());
        return responseDto;
    }
}
