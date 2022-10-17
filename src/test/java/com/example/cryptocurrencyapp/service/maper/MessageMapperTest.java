package com.example.cryptocurrencyapp.service.maper;

import com.example.cryptocurrencyapp.dto.ApiMessageDto;
import com.example.cryptocurrencyapp.dto.MessageResponseDto;
import com.example.cryptocurrencyapp.model.ApiMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MessageMapperTest {
    private static final String DATA_NOT_DETECTED = "haven't been detected after websocket session";

    @InjectMocks
    private MessageMapper messageMapper;
    private ApiMessage apiMessage;
    private ApiMessageDto apiMessageDto;
    private MessageResponseDto messageResponseDto;
    private MessageResponseDto badMessageDto;

    @BeforeEach
    void setUp() {
        apiMessage = new ApiMessage();
        apiMessage.setSymbolId("COINBASE_SPOT_BTC_USD");
        apiMessage.setTimeExchange("2022-10-17");
        apiMessage.setPrice("3950.85");

        apiMessageDto = new ApiMessageDto();
        apiMessageDto.setSymbolId("COINBASE_SPOT_BTC_USD");
        apiMessageDto.setTimeExchange("2022-10-17");
        apiMessageDto.setPrice("3950.85");

        messageResponseDto = new MessageResponseDto();
        messageResponseDto.setSymbolId("COINBASE_SPOT_BTC_USD");
        messageResponseDto.setTimeExchange("2022-10-17");
        messageResponseDto.setPriceUsd("3950.85");

        badMessageDto = new MessageResponseDto();
        badMessageDto.setSymbolId("COINBASE_SPOT_ETH_USD");
        badMessageDto.setTimeExchange(DATA_NOT_DETECTED);
        badMessageDto.setPriceUsd(DATA_NOT_DETECTED);
    }

    @Test
    void mapModel_validMessageDto_ok() {
        ApiMessage result = messageMapper.toModel(apiMessageDto);
        Assertions.assertEquals(apiMessage.getSymbolId(), result.getSymbolId());
        Assertions.assertEquals(apiMessage.getTimeExchange(), result.getTimeExchange());
        Assertions.assertEquals(apiMessage.getPrice(), result.getPrice());
    }

    @Test
    void mapDto_validMessage_ok() {
        MessageResponseDto result = messageMapper.toDto(apiMessage);
        Assertions.assertEquals(messageResponseDto.getSymbolId(), result.getSymbolId());
        Assertions.assertEquals(messageResponseDto.getTimeExchange(), result.getTimeExchange());
        Assertions.assertEquals(messageResponseDto.getPriceUsd(), result.getPriceUsd());
    }

    @Test
    void toBlankDto_validSymbol_ok() {
        String ethSymbolId = "COINBASE_SPOT_ETH_USD";
        MessageResponseDto result = messageMapper.toBlankDto(ethSymbolId);
        Assertions.assertEquals(badMessageDto.getSymbolId(), result.getSymbolId());
        Assertions.assertEquals(badMessageDto.getTimeExchange(), DATA_NOT_DETECTED);
        Assertions.assertEquals(badMessageDto.getPriceUsd(), DATA_NOT_DETECTED);
    }
}
