package com.example.cryptocurrencyapp.service;

import com.example.cryptocurrencyapp.model.Crypto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface CryptoService {
    Page<Crypto> getAll(PageRequest pageRequest);

    void refreshCryptoList();
}
