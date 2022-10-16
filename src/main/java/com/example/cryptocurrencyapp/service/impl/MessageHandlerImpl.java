package com.example.cryptocurrencyapp.service.impl;

import com.example.cryptocurrencyapp.dto.ApiMessageDto;
import com.example.cryptocurrencyapp.model.ApiMessage;
import com.example.cryptocurrencyapp.service.maper.MessageMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Queue;
import liquibase.repackaged.org.apache.commons.collections4.queue.CircularFifoQueue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageHandlerImpl {
    private final MessageMapper messageMapper;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Queue<ApiMessage> currentDataQueue = new CircularFifoQueue<>(100);
    private boolean valve = true;

    {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public void handleMessage(String message) {
        try {
            ApiMessageDto apiMessageDto = objectMapper.readValue(message, ApiMessageDto.class);
            if (valve) {
                currentDataQueue.offer(messageMapper.toModel(apiMessageDto));
            }
            System.out.println(apiMessageDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Cannot get message from " + message + "\n" + e);
        }
    }

    public Queue<ApiMessage> getCurrentQueue() {
        return currentDataQueue;
    }

    public void clearQueue() {
        currentDataQueue.clear();
    }

    public void makeValveOpen() {
        valve = true;
    }

    public void makeValveClose() {
        valve = false;
    }
}
