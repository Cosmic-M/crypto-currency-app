package com.example.cryptocurrencyapp.service.impl;

import com.example.cryptocurrencyapp.dto.ApiMessageDto;
import com.example.cryptocurrencyapp.model.ApiMessage;
import com.example.cryptocurrencyapp.service.maper.MessageMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageHandlerImpl {
    private final MessageMapper messageMapper;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<String, ApiMessage> messagesMap = new ConcurrentHashMap<>();
    private boolean valve = true;

    {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public void handleMessage(String message) {
        try {
            ApiMessageDto apiMessageDto = objectMapper.readValue(message, ApiMessageDto.class);
            messagesMap.put(apiMessageDto.getSymbolId(), messageMapper.toModel(apiMessageDto));
            System.out.println(apiMessageDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Cannot get message from " + message + "\n" + e);
        }
    }

    public Map<String, ApiMessage> getMessageMap() {
        return messagesMap;
    }
}
