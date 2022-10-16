package com.example.cryptocurrencyapp.service.impl;

import com.example.cryptocurrencyapp.dto.MessageResponseDto;
import com.example.cryptocurrencyapp.model.ApiMessage;
import com.example.cryptocurrencyapp.service.MessageServiceComposer;
import com.example.cryptocurrencyapp.service.maper.MessageMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceComposerImpl implements MessageServiceComposer {
    private final MessageHandlerImpl messageHandler;
    private final MessageMapper messageMapper;

    @Override
    public List<MessageResponseDto> composeResponse(List<String> cryptoGroup) {
        List<MessageResponseDto> result = new ArrayList<>();
        List<String> symbolIds = new ArrayList<>();
        for (String crypto : cryptoGroup) {
            symbolIds.add("COINBASE_SPOT_" + crypto + "_USD");
        }
        Queue<ApiMessage> queueMessages = messageHandler.getCurrentQueue();
        if (queueMessages.isEmpty()) {
            return symbolIds.stream()
                    .map(messageMapper::toBlankDto)
                    .collect(Collectors.toList());
        }
        for (String key : symbolIds) {
            Optional<ApiMessage> apiMessage = queueMessages.stream()
                    .filter(element -> element.getSymbolId() != null)
                    .filter(element -> element.getSymbolId().equals(key))
                    .findFirst();
            if (apiMessage.isPresent()) {
                result.add(messageMapper.toDto(apiMessage.get()));
            } else {
                result.add(messageMapper.toBlankDto(key));
            }
        }
        return result;
    }
}
