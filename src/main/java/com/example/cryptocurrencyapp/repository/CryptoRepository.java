package com.example.cryptocurrencyapp.repository;

import com.example.cryptocurrencyapp.model.Crypto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoRepository extends JpaRepository<Crypto, String> {
}
