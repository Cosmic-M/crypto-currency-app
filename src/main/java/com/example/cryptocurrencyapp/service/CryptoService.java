package com.example.cryptocurrencyapp.service;

import com.example.cryptocurrencyapp.model.Asset;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface CryptoService {
    Page<Asset> getAll(PageRequest pageRequest);

    void getAssetsFromApi();
}
