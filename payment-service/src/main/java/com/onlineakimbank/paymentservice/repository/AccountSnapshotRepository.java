package com.onlineakimbank.paymentservice.repository;

import com.onlineakimbank.paymentservice.entity.AccountSnapshot;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AccountSnapshotRepository {

    private final RedisTemplate<String, AccountSnapshot> redisTemplate;

    private static final String KEY_PREFIX = "account:snapshot:";

    public AccountSnapshot save(AccountSnapshot snapshot) {

        String key = KEY_PREFIX + snapshot.getAccountId();
        redisTemplate.opsForValue().set(key, snapshot);

        return snapshot;
    }

    public Optional<AccountSnapshot> findById(String accountId) {

        String key = KEY_PREFIX + accountId;
        AccountSnapshot snapshot = redisTemplate.opsForValue().get(key);

        return Optional.ofNullable(snapshot);
    }

    public void delete(String accountId) {

        redisTemplate.delete(KEY_PREFIX + accountId);
    }

    public boolean exists(String accountId) {

        return Boolean.TRUE.equals(
                redisTemplate.hasKey(KEY_PREFIX + accountId)
        );
    }
}
