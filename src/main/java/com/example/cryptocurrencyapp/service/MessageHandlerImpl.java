package com.example.cryptocurrencyapp.service;

import com.example.cryptocurrencyapp.dto.ApiMessageDto;
import com.example.cryptocurrencyapp.model.ApiMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MessageHandlerImpl  {
    private final MessageMapper messageMapper;
    private final ObjectMapper objectMapper = new ObjectMapper();

    {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    public final List<ApiMessage> apiMessageList = new ArrayList<>();

    public void handleMessage(String message) {
        try {
            ApiMessageDto apiMessageDto = objectMapper.readValue(message, ApiMessageDto.class);
            apiMessageList.add(messageMapper.toModel(apiMessageDto));
            System.out.println(apiMessageDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Cannot get message from " + message + "\n" + e);
        }
    }
}
