package com.example.cryptocurrencyapp.controller;

import com.example.cryptocurrencyapp.dto.AssetResponseDto;
import com.example.cryptocurrencyapp.service.CryptoService;
import com.example.cryptocurrencyapp.service.maper.AssetMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cryptos")
public class CryptoController {
    private final AssetMapper assetMapper;
    private final CryptoService cryptoService;

    @GetMapping("/all")
    @ApiOperation(value = "get all cryptos from DB")
    public List<AssetResponseDto> getAllCryptoCurrencies(@RequestParam(defaultValue = "20")
                                                         @ApiParam(value = "default value is 20")
                                                         Integer count,
                                                         @RequestParam(defaultValue = "0")
                                                         @ApiParam(value = "default value is 0")
                                                         Integer page) {
        return cryptoService.getAll(PageRequest.of(page, count)).stream()
                .map(assetMapper::toDto)
                .toList();
    }
}
