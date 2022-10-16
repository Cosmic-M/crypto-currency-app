package com.example.cryptocurrencyapp.service.maper;

import com.example.cryptocurrencyapp.dto.ApiMessageDto;
import com.example.cryptocurrencyapp.dto.MessageResponseDto;
import com.example.cryptocurrencyapp.model.ApiMessage;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {
    private static final String DATA_NOT_DETECTED = "haven't been detected after websocket session";

    public ApiMessage toModel(ApiMessageDto apiMessageDto) {
        ApiMessage apiMessage = new ApiMessage();
        apiMessage.setSymbolId(apiMessageDto.getSymbol_id());
        apiMessage.setTimeExchange(apiMessageDto.getTime_exchange());
        apiMessage.setPrice(apiMessageDto.getPrice());
        return apiMessage;
    }

    public MessageResponseDto toDto(ApiMessage apiMessage) {
        MessageResponseDto responseDto = new MessageResponseDto();
        responseDto.setTimeExchange(apiMessage.getTimeExchange());
        responseDto.setSymbolId(apiMessage.getSymbolId());
        responseDto.setPriceUsd(apiMessage.getPrice());
        return responseDto;
    }

    public MessageResponseDto toBlankDto(String symbolId) {
        MessageResponseDto responseDto = new MessageResponseDto();
        responseDto.setTimeExchange(DATA_NOT_DETECTED);
        responseDto.setSymbolId(symbolId);
        responseDto.setPriceUsd(DATA_NOT_DETECTED);
        return responseDto;
    }
}
