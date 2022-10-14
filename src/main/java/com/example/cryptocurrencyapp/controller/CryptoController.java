package com.example.cryptocurrencyapp.controller;

import com.example.cryptocurrencyapp.dto.AssetResponseDto;
import com.example.cryptocurrencyapp.service.AssetMapper;
import com.example.cryptocurrencyapp.service.CryptoService;
import com.example.cryptocurrencyapp.service.WebSocketCryptoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cryptos")
public class CryptoController {
    private final AssetMapper assetMapper;
    private final CryptoService cryptoService;
    private final WebSocketCryptoService webSocketCryptoService;

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

    @GetMapping("/price")
    @ApiOperation(value = "get current price to specific crypto")
    public String getPriceInform(String trader, @RequestParam List<String> cryptoGroup) {
        webSocketCryptoService.sendRequest(trader, cryptoGroup);
        return "Done!";
    }
}
