package com.example.cryptocurrencyapp.service.impl;

import com.example.cryptocurrencyapp.dto.MessageResponseDto;
import com.example.cryptocurrencyapp.model.ApiMessage;
import com.example.cryptocurrencyapp.service.MessageServiceComposer;
import com.example.cryptocurrencyapp.service.maper.MessageMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        Map<String, ApiMessage> messageMap = messageHandler.getMessageMap();
        for (String key : symbolIds) {
            ApiMessage apiMessage = messageMap.get(key);
            if (apiMessage != null) {
                result.add(messageMapper.toDto(apiMessage));
            } else {
                result.add(messageMapper.toBlankDto(key));
            }
        }
        return result;
    }
}
