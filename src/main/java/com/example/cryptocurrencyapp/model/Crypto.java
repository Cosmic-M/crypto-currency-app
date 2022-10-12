package com.example.cryptocurrencyapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "crypto_currencies")
public class Crypto {
    @Id
    @Column(name = "asset_id_quote", nullable = false)
    private String assetIdQuote;
    private LocalDateTime time;
    private String rate;
}
