package com.example.cryptocurrencyapp.service;

import com.example.cryptocurrencyapp.dto.ApiCryptoDto;
import com.example.cryptocurrencyapp.dto.ApiResponseDto;
import com.example.cryptocurrencyapp.model.Crypto;
import com.example.cryptocurrencyapp.repository.CryptoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Log4j2
@Service
@EnableScheduling
@RequiredArgsConstructor
public class CryptoServiceImpl implements CryptoService {
    private final HttpClient httpClient;
    private final CryptoMapper cryptoMapper;
    private final CryptoRepository cryptoRepository;
    @Value(value = "${coinApiLink}")
    private String linkToApi;
    @Value(value = "${API_KEY}")
    private String key;

    @Override
    public List<Crypto> getAll() {
        return cryptoRepository.findAll();
    }

    @Override
    @PostConstruct
    @Scheduled(cron = "0 30 8 * * ?", zone = "Europe/Kiev")
    public void refreshCryptoList() {
        log.info("syncExternalCharacters was called at " + LocalDateTime.now());

        ApiResponseDto responseDto = httpClient.get(linkToApi + key, ApiResponseDto.class);
        List<ApiCryptoDto> toSave = new ArrayList<>(Arrays.stream(responseDto.getRates()).toList());
        List<Crypto> cryptoList = toSave.stream()
                .map(cryptoMapper::toModel)
                .collect(Collectors.toList());
        cryptoRepository.saveAll(cryptoList);
    }

    List<Crypto> getListToSave(ApiResponseDto responseDto) {
        return null;
    }
}
