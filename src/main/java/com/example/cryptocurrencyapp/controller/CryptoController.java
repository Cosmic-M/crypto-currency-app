package com.example.cryptocurrencyapp.controller;

import com.example.cryptocurrencyapp.dto.AssetResponseDto;
import com.example.cryptocurrencyapp.dto.MessageResponseDto;
import com.example.cryptocurrencyapp.model.ApiMessage;
import com.example.cryptocurrencyapp.service.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cryptos")
public class CryptoController {
    private final AssetMapper assetMapper;
    private final CryptoService cryptoService;
    private final WebSocketCryptoService webSocketCryptoService;
    private final MessageHandlerImpl messageHandler;
    private final MessageMapper messageMapper;

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
    public List<MessageResponseDto> getPriceInform(@RequestParam List<String> cryptoGroup) {
        List<MessageResponseDto> result = new ArrayList<>();
        WebsocketClientEndpoint clientEndpoint = webSocketCryptoService.sendRequest(cryptoGroup);
        List<String> symbolIds = new ArrayList<>();
        for (String crypto : cryptoGroup) {
            symbolIds.add("COINBASE_SPOT_" + crypto + "_USD");
        }
        try {
            Thread.sleep(3000);
            int timing = 0;
            while (symbolIds.size() != 0 && timing < 5) {
                for (int i = 0; i < messageHandler.apiMessageList.size() && i < 100; i++) {
                    ApiMessage apiMessage = messageHandler.apiMessageList.get(i);
                    if (symbolIds.contains(apiMessage.getSymbolId())) {
                        MessageResponseDto message = messageMapper.toDto(apiMessage);
                        result.add(message);
                        symbolIds.remove(apiMessage.getSymbolId());
                    }
                }
                timing++;
                Thread.sleep(300);
            }
            clientEndpoint.onClose(null, null);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
