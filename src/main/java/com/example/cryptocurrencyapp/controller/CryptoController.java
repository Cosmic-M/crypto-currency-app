package com.example.cryptocurrencyapp.controller;

import com.example.cryptocurrencyapp.dto.CryptoResponseDto;
import com.example.cryptocurrencyapp.service.CryptoMapper;
import com.example.cryptocurrencyapp.service.CryptoService;
import com.example.cryptocurrencyapp.util.SortCryptoUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cryptos")
public class CryptoController {
    private final CryptoMapper cryptoMapper;
    private final CryptoService cryptoService;

    @GetMapping("/all")
    @ApiOperation(value = "get all cryptos from DB")
    public List<CryptoResponseDto> getAll(@RequestParam(defaultValue = "20")
                                              @ApiParam(value = "default value is 20")
                                              Integer count,
                                          @RequestParam(defaultValue = "0")
                                              @ApiParam(value = "default value is 0")
                                              Integer page,
                                          @RequestParam(defaultValue = "assetIdQuote")
                                              @ApiParam(value = "default value is "
                                                      + "assetIdQuote but you can apply also "
                                                      + "('rate' as the relevant meaning")
                                              String sortBy) {
        Sort sort = SortCryptoUtil.getSortingCryptos(sortBy);

        return cryptoService.getAll(PageRequest.of(page, count, sort)).stream()
                .map(cryptoMapper::toDto)
                .toList();
    }
}
