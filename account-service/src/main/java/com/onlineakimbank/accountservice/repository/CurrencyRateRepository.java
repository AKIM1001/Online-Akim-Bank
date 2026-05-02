package com.onlineakimbank.accountservice.repository;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class CurrencyRateRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private final HashOperations<String, String, String> hashOps;
    private static final String KEY = "currency_rates";

    public CurrencyRateRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOps = redisTemplate.opsForHash();
    }

    public void saveRate(String from, String to, BigDecimal rate) {
        hashOps.put(KEY, from + ":" + to, rate.toPlainString());
    }

    public BigDecimal getRate(String from, String to) {
        String value = hashOps.get(KEY, from + ":" + to);
        if (value == null) return null;
        return new BigDecimal(value);
    }
}

