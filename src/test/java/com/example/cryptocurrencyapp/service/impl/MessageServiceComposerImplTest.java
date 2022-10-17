package com.example.cryptocurrencyapp.service.impl;

import com.example.cryptocurrencyapp.dto.MessageResponseDto;
import com.example.cryptocurrencyapp.model.ApiMessage;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.util.List;
import java.util.Queue;
import liquibase.repackaged.org.apache.commons.collections4.queue.CircularFifoQueue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ComponentScan(basePackages = "com.example.cryptocurrencyapp")
class MessageServiceComposerImplTest {
    private static final String DATA_NOT_DETECTED = "haven't been detected after websocket session";

    @Autowired
    private MessageServiceComposerImpl messageServiceComposer;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MessageHandlerImpl messageHandler;
    private Queue<ApiMessage> queueMessages;
    private List<String> symbols;
    private MessageResponseDto firstDto;
    private MessageResponseDto secondDto;
    private MessageResponseDto btcBlankDto;
    private MessageResponseDto ethBlankDto;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
        queueMessages = new CircularFifoQueue<>(100);

        ApiMessage firstMessage = new ApiMessage();
        firstMessage.setPrice("123,45");
        firstMessage.setSymbolId("COINBASE_SPOT_BTC_USD");
        firstMessage.setTimeExchange("2022-10-17");

        ApiMessage secondMessage = new ApiMessage();
        secondMessage.setPrice("95,205");
        secondMessage.setSymbolId("COINBASE_SPOT_ETH_USD");
        secondMessage.setTimeExchange("2022-10-17");

        queueMessages.add(firstMessage);
        queueMessages.add(secondMessage);

        String btcSymbolId = "BTC";
        String ethSymbolId = "ETH";
        symbols = List.of(btcSymbolId, ethSymbolId);

        firstDto = new MessageResponseDto();
        firstDto.setSymbolId("COINBASE_SPOT_BTC_USD");
        firstDto.setTimeExchange("2022-10-17");
        firstDto.setPriceUsd("123,45");

        secondDto = new MessageResponseDto();
        secondDto.setSymbolId("COINBASE_SPOT_ETH_USD");
        secondDto.setTimeExchange("2022-10-17");
        secondDto.setPriceUsd("95,205");

        btcBlankDto = new MessageResponseDto();
        btcBlankDto.setSymbolId("COINBASE_SPOT_BTC_USD");
        btcBlankDto.setTimeExchange(DATA_NOT_DETECTED);
        btcBlankDto.setPriceUsd(DATA_NOT_DETECTED);

        ethBlankDto = new MessageResponseDto();
        ethBlankDto.setSymbolId("COINBASE_SPOT_ETH_USD");
        ethBlankDto.setTimeExchange(DATA_NOT_DETECTED);
        ethBlankDto.setPriceUsd(DATA_NOT_DETECTED);
    }

    @Test
    void composeResponse_allDataAvailable_ok() {
        Mockito.when(messageHandler.getCurrentQueue()).thenReturn(queueMessages);
        List<MessageResponseDto> resultMessages = messageServiceComposer.composeResponse(symbols);

        Assertions.assertEquals(firstDto.getSymbolId(), resultMessages.get(0).getSymbolId());
        Assertions.assertEquals(firstDto.getTimeExchange(),
                resultMessages.get(0).getTimeExchange());
        Assertions.assertEquals(firstDto.getPriceUsd(), resultMessages.get(0).getPriceUsd());

        Assertions.assertEquals(secondDto.getSymbolId(), resultMessages.get(1).getSymbolId());
        Assertions.assertEquals(secondDto.getTimeExchange(),
                resultMessages.get(1).getTimeExchange());
        Assertions.assertEquals(secondDto.getPriceUsd(), resultMessages.get(1).getPriceUsd());
    }

    @Test
    void composeResponse_oneBlankMessage_ok() {
        queueMessages.remove();
        Mockito.when(messageHandler.getCurrentQueue()).thenReturn(queueMessages);
        List<MessageResponseDto> resultMessages = messageServiceComposer.composeResponse(symbols);

        Assertions.assertEquals(btcBlankDto.getSymbolId(), resultMessages.get(0).getSymbolId());
        Assertions.assertEquals(btcBlankDto.getTimeExchange(),
                resultMessages.get(0).getTimeExchange());
        Assertions.assertEquals(btcBlankDto.getPriceUsd(), resultMessages.get(0).getPriceUsd());

        Assertions.assertEquals(secondDto.getSymbolId(), resultMessages.get(1).getSymbolId());
        Assertions.assertEquals(secondDto.getTimeExchange(),
                resultMessages.get(1).getTimeExchange());
        Assertions.assertEquals(secondDto.getPriceUsd(), resultMessages.get(1).getPriceUsd());
    }

    @Test
    void composeResponse_queueIsEmpty_ok() {
        queueMessages.remove();
        queueMessages.remove();
        Mockito.when(messageHandler.getCurrentQueue()).thenReturn(queueMessages);
        List<MessageResponseDto> resultMessages = messageServiceComposer.composeResponse(symbols);

        Assertions.assertEquals(btcBlankDto.getSymbolId(), resultMessages.get(0).getSymbolId());
        Assertions.assertEquals(btcBlankDto.getTimeExchange(),
                resultMessages.get(0).getTimeExchange());
        Assertions.assertEquals(btcBlankDto.getPriceUsd(), resultMessages.get(0).getPriceUsd());

        Assertions.assertEquals(ethBlankDto.getSymbolId(), resultMessages.get(1).getSymbolId());
        Assertions.assertEquals(ethBlankDto.getTimeExchange(),
                resultMessages.get(1).getTimeExchange());
        Assertions.assertEquals(ethBlankDto.getPriceUsd(), resultMessages.get(1).getPriceUsd());
    }
}
