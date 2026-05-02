package com.onlineakimbank.authservice.service;

import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class TokenCacheService {

    private final RedisTemplate<String, AccessTokenResponse> redisTemplate;

    @Value("${keycloak.realm}")
    private String keycloakRealm;

    @Value("${keycloak.auth-server-url}")
    private String keycloakAuthServerUrl;

    @Value("${keycloak.credentials.client-id}")
    private String keycloakClientId;

    @Value("${keycloak.credentials.secret}")
    private String keycloakClientSecret;

    private static final Logger logger = Logger.getLogger(TokenCacheService.class.getName());

    public AccessTokenResponse getCacheToken(String userId, String cardNumber, String password) {
        String key = "AUTH_TOKEN::" + userId;
        logger.info("[Authenticating user: " + cardNumber + "]");
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(keycloakAuthServerUrl)
                .realm(keycloakRealm)
                .grantType(OAuth2Constants.PASSWORD)
                .clientId(keycloakClientId)
                .clientSecret(keycloakClientSecret)
                .username(cardNumber)
                .password(password)
                .build();

        AccessTokenResponse accessToken = keycloak.tokenManager().getAccessToken();
        redisTemplate.opsForValue().set(key, accessToken, Duration.ofSeconds(accessToken.getExpiresIn()));
        logger.info("[Generated tokens for user: " + cardNumber + ", userId: " + userId + "]");
        return accessToken;
    }
}

