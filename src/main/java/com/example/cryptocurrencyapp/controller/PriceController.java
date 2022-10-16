package com.example.cryptocurrencyapp.controller;

import com.example.cryptocurrencyapp.dto.ApiHistoryPriceDto;
import com.example.cryptocurrencyapp.dto.MessageResponseDto;
import com.example.cryptocurrencyapp.service.HistoryPriceService;
import com.example.cryptocurrencyapp.service.WebSocketCryptoService;
import com.example.cryptocurrencyapp.service.impl.MessageHandlerImpl;
import com.example.cryptocurrencyapp.service.MessageServiceComposer;
import java.util.List;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/prices")
public class PriceController {
    private final HistoryPriceService historyPriceService;
    private final WebSocketCryptoService webSocketCryptoService;
    private final MessageServiceComposer messageServiceComposer;
    private final MessageHandlerImpl messageHandler;

    @GetMapping("/current")
    @ApiOperation(value = "get current price to specific crypto")
    public List<MessageResponseDto> getCurrentPrice(@RequestParam List<String> cryptoGroup) {
        messageHandler.clearQueue();
        messageHandler.makeValveOpen();
        webSocketCryptoService.sendRequest(cryptoGroup);
        try {
            Thread.sleep(2500);
            messageHandler.makeValveClose();
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException("Method sleep threw exception. " + e);
        }
        return messageServiceComposer.composeResponse(cryptoGroup);
    }

    @GetMapping("/history")
    @ApiOperation(value = "get history price for specific crypto at determine time")
    public List<ApiHistoryPriceDto> getHistoricalPrices(@RequestParam @ApiParam(value = "Please, assign crypto")
                                                        String cryptoName,
                                                        @RequestParam @ApiParam(value =
                                                                "put valid data format, like YYYY-MM-DD")
                                                        String date) {
        return historyPriceService.getHistoryPrice(cryptoName, date);
    }
}
