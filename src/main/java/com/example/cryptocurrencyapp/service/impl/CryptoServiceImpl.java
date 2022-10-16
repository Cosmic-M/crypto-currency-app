package com.example.cryptocurrencyapp.service.impl;

import com.example.cryptocurrencyapp.dto.ApiAssetDto;
import com.example.cryptocurrencyapp.model.Asset;
import com.example.cryptocurrencyapp.repository.AssetRepository;
import com.example.cryptocurrencyapp.service.CryptoService;
import com.example.cryptocurrencyapp.service.ResponseParser;
import com.example.cryptocurrencyapp.service.maper.AssetMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class CryptoServiceImpl implements CryptoService {
    private final ResponseParser responseParser;
    private final AssetMapper assetMapper;
    private final AssetRepository assetRepository;

    @Value(value = "${assetsRestApiLink}")
    private String link;
    @Value(value = "${API_KEY}")
    private String key;

    @Override
    public Page<Asset> getAll(PageRequest pageRequest) {
        return assetRepository.findAll(pageRequest);
    }

    @Override
    @PostConstruct
    @Scheduled(cron = "0 30 8 * * ?", zone = "Europe/Kiev")
    public void getAssetsFromApi() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(link)
                .addHeader("X-CoinAPI-Key", key)
                .build();
        try {
            Response response = client.newCall(request).execute();
            ApiAssetDto[] apiAssetDtos = responseParser.parse(response, ApiAssetDto[].class);
            List<Asset> assets = Arrays.stream(apiAssetDtos)
                    .filter(crypto -> crypto.getCryptoType() == 1)
                    .map(assetMapper::toModel)
                    .toList();
            assetRepository.saveAll(assets);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
