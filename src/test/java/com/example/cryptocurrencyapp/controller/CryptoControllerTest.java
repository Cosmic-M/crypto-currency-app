package com.example.cryptocurrencyapp.controller;

import com.example.cryptocurrencyapp.dto.AssetResponseDto;
import com.example.cryptocurrencyapp.model.Asset;
import com.example.cryptocurrencyapp.service.CryptoService;
import com.example.cryptocurrencyapp.service.maper.AssetMapper;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CryptoControllerTest {
    @MockBean
    private AssetMapper assetMapper;
    @MockBean
    private CryptoService cryptoService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void getAllCryptoCurrencies_validData_ok() {
        Asset btc = new Asset();
        btc.setId(1L);
        btc.setAssetId("BTC");
        btc.setTitle("Bitcoin");

        Asset nis = new Asset();
        nis.setId(2L);
        nis.setAssetId("NIS");
        nis.setTitle("NIS");

        Asset ltc = new Asset();
        ltc.setId(3L);
        ltc.setAssetId("LTC");
        ltc.setTitle("Litecoin");

        AssetResponseDto btcDto = new AssetResponseDto();
        btcDto.setId(1L);
        btcDto.setAssetId("BTC");
        btcDto.setTitle("Bitcoin");

        AssetResponseDto nisDto = new AssetResponseDto();
        nisDto.setId(2L);
        nisDto.setAssetId("NIS");
        nisDto.setTitle("NIS");

        AssetResponseDto ltcDto = new AssetResponseDto();
        ltcDto.setId(3L);
        ltcDto.setAssetId("LTC");
        ltcDto.setTitle("Litecoin");

        List<Asset> mockAssetList = List.of(btc, nis, ltc);
        final Page<Asset> pages = new PageImpl<>(mockAssetList);
        PageRequest pageRequest = PageRequest.of(0, 20);

        Mockito.when(cryptoService.getAll(pageRequest)).thenReturn(pages);
        Mockito.when(assetMapper.toDto(btc)).thenReturn(btcDto);
        Mockito.when(assetMapper.toDto(nis)).thenReturn(nisDto);
        Mockito.when(assetMapper.toDto(ltc)).thenReturn(ltcDto);

        RestAssuredMockMvc.when()
                .get("/cryptos/all")
                .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(3))
                .body("[0].id", Matchers.equalTo(1))
                .body("[0].assetId", Matchers.equalTo("BTC"))
                .body("[0].title", Matchers.equalTo("Bitcoin"))
                .body("[1].id", Matchers.equalTo(2))
                .body("[1].assetId", Matchers.equalTo("NIS"))
                .body("[1].title", Matchers.equalTo("NIS"))
                .body("[2].id", Matchers.equalTo(3))
                .body("[2].assetId", Matchers.equalTo("LTC"))
                .body("[2].title", Matchers.equalTo("Litecoin"));
    }
}
