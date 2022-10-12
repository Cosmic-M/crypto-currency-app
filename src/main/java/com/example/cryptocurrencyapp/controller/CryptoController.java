package com.example.cryptocurrencyapp.controller;

import com.example.cryptocurrencyapp.dto.CryptoResponseDto;
import com.example.cryptocurrencyapp.service.CryptoMapper;
import com.example.cryptocurrencyapp.service.CryptoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cryptos")
public class CryptoController {
    private final CryptoMapper cryptoMapper;
    private final CryptoService cryptoService;

    @GetMapping("/all")
    public List<CryptoResponseDto> getAll() {
        return cryptoService.getAll().stream()
                .map(cryptoMapper::toDto)
                .toList();
    }
}
