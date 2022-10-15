package com.example.cryptocurrencyapp.service;

import com.example.cryptocurrencyapp.dto.ApiHistoryPriceDto;
import java.util.List;

public interface HistoryPriceService {
    List<ApiHistoryPriceDto> getHistoryPrice(String cryptoName, String date);
}
