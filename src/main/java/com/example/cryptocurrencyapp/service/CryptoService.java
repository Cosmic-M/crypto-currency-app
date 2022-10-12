package com.example.cryptocurrencyapp.service;

import com.example.cryptocurrencyapp.model.Crypto;

import java.util.List;

public interface CryptoService {
    List<Crypto> getAll();

    void refreshCryptoList();
}
