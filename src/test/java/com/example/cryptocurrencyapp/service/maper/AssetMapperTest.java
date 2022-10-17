package com.example.cryptocurrencyapp.service.maper;

import com.example.cryptocurrencyapp.dto.ApiAssetDto;
import com.example.cryptocurrencyapp.dto.AssetResponseDto;
import com.example.cryptocurrencyapp.model.Asset;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AssetMapperTest {
    @InjectMocks
    private AssetMapper assetMapper;
    private Asset asset;
    private AssetResponseDto assetResponseDto;
    private ApiAssetDto apiAssetDto;

    @BeforeEach
    void setUp() {
        asset = new Asset();
        asset.setId(1);
        asset.setAssetId("BTC");
        asset.setTitle("Bitcoin");

        assetResponseDto = new AssetResponseDto();
        assetResponseDto.setId(1L);
        assetResponseDto.setAssetId("BTC");
        assetResponseDto.setTitle("Bitcoin");

        apiAssetDto = new ApiAssetDto();
        apiAssetDto.setAssetId("BTC");
        apiAssetDto.setName("Bitcoin");
    }

    @Test
    void mapModel_validApiAssetDto_ok() {
        Asset result = assetMapper.toModel(apiAssetDto);
        Assertions.assertEquals(asset.getAssetId(), result.getAssetId());
        Assertions.assertEquals(asset.getTitle(), result.getTitle());
    }

    @Test
    void mapDto_validAsset_ok() {
        AssetResponseDto result = assetMapper.toDto(asset);
        Assertions.assertEquals(assetResponseDto.getId(), result.getId());
        Assertions.assertEquals(assetResponseDto.getAssetId(), result.getAssetId());
        Assertions.assertEquals(assetResponseDto.getTitle(), result.getTitle());
    }
}
