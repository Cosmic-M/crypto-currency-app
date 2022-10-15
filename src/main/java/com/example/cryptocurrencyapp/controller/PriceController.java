package com.example.cryptocurrencyapp.controller;

import com.example.cryptocurrencyapp.dto.ApiHistoryPriceDto;
import com.example.cryptocurrencyapp.dto.MessageResponseDto;
import com.example.cryptocurrencyapp.model.ApiMessage;
import com.example.cryptocurrencyapp.service.*;
import com.example.cryptocurrencyapp.service.maper.MessageMapper;
import com.example.cryptocurrencyapp.util.DateTimePatternUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/prices")
public class PriceController {
    private final HistoryPriceService historyPriceService;
    private final WebSocketCryptoService webSocketCryptoService;
    private final MessageHandlerImpl messageHandler;
    private final MessageMapper messageMapper;

    @GetMapping("/current")
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

    @GetMapping("/history")
    @ApiOperation(value = "get history price for specific crypto at determine time")
    public List<ApiHistoryPriceDto> getHistoricalPrices(@RequestParam @ApiParam(value = "Please, assign crypto")
                                                        String cryptoName,
                                                        @RequestParam @ApiParam(value =
                                                                "put valid data format, like YYYY-MM-DD")
                                                        @DateTimeFormat(pattern =
                                                                DateTimePatternUtil.DATE_PATTERN) String date) {
        return historyPriceService.getHistoryPrice(cryptoName, date);
    }
}
