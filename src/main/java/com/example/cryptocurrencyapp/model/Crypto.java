package com.example.cryptocurrencyapp.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "crypto_currencies")
public class Crypto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "asset_id_quote", nullable = false)
    private String assetIdQuote;
    @Column(name = "date_time", nullable = false)
    private LocalDateTime time;
    private String rate;
}
