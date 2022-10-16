package com.example.cryptocurrencyapp.service.impl;

import com.example.cryptocurrencyapp.dto.ApiHistoryPriceDto;
import com.example.cryptocurrencyapp.service.HistoryPriceService;
import com.example.cryptocurrencyapp.service.ResponseParser;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryPriceServiceImpl implements HistoryPriceService {
    private final ResponseParser responseParser;
    @Value(value = "${historyRestApiLink}")
    private String link;
    @Value(value = "${API_KEY}")
    private String key;

    @Override
    public List<ApiHistoryPriceDto> getHistoryPrice(String cryptoName, String date) {
        StringBuilder builder = new StringBuilder();
        builder.append(link)
                .append("/")
                .append("COINBASE")
                .append("_")
                .append("SPOT")
                .append("_")
                .append(cryptoName)
                .append("_")
                .append("USD")
                .append("/")
                .append("history")
                .append("?")
                .append("time_start=")
                .append(date)
                .append("&")
                .append("limit=")
                .append("20");

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(builder.toString())
                .addHeader("X-CoinAPI-Key", key)
                .build();
        try {
            Response response = client.newCall(request).execute();
            ApiHistoryPriceDto[] apiHistoryPriceDtos = responseParser.parse(response, ApiHistoryPriceDto[].class);
            return Arrays.stream(apiHistoryPriceDtos).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
