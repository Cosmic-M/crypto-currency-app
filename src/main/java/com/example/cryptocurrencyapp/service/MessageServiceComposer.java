package com.example.cryptocurrencyapp.service;

import com.example.cryptocurrencyapp.dto.MessageResponseDto;
import java.util.List;

public interface MessageServiceComposer {
    List<MessageResponseDto> composeResponse(List<String> cryptoGroup);
}
