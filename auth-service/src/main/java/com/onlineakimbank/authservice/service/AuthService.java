package com.onlineakimbank.authservice.service;

import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.logging.Logger;



@Service
@RequiredArgsConstructor
public class AuthService {
    private static Logger logger = Logger.getLogger(AuthService.class.getName());

    @Value("${keycloak.realm}")
    private String keycloakRealm;
    private final Keycloak keycloakAdminClient;

    @Value("${keycloak.credentials.client-id}")
    private String clientId;

    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    @Value("${keycloak.auth-server-url}")
    private String keycloakServerUrl;

    private final TokenCacheService tokenCacheService;

    public String registerUser(String firstName, String lastName,
                               String email, String password) {
        return registerUserWithRole(firstName, lastName, email, password, "USER");
    }

    public String registerAdmin(String firstName, String lastName,
                                String email, String password) {
        return registerUserWithRole(firstName, lastName, email, password, "ADMIN");
    }
    public String registerUserWithRole(String firstName, String lastName,
                                       String email, String password, String roleName) {

        RealmResource realm = keycloakAdminClient.realm(keycloakRealm);
        UsersResource usersResource = realm.users();

        UserRepresentation user = new UserRepresentation();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setEnabled(true);

        Response response = usersResource.create(user);
        if (response.getStatus() != 201) {
            if (response.getStatus() == 409) {
                throw new RuntimeException("[User already exists]");
            }
            throw new RuntimeException("[Failed to register user: " + response.getStatusInfo() + "]");
        }

        String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(false);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        realm.users().get(userId).resetPassword(credential);

        RoleRepresentation role = realm.roles().get(roleName).toRepresentation();

        realm.users().get(userId)
                .roles()
                .realmLevel()
                .add(Collections.singletonList(role));

        logger.info("[User successfully registered: " + userId + "]");
        return userId;
    }


    public AccessTokenResponse authenticateUser(String username, String password, String email, String userLoginEventPassword) {
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            throw new IllegalArgumentException("Username and password must not be empty");
        }

        try {

            Keycloak keycloakUserClient = KeycloakBuilder.builder()
                    .serverUrl(keycloakServerUrl)
                    .realm(keycloakRealm)
                    .grantType(OAuth2Constants.PASSWORD)
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .username(username)
                    .password(password)
                    .build();

            AccessTokenResponse tokenResponse = keycloakUserClient.tokenManager().getAccessToken();

            logger.info("[User authenticated successfully: " + username + "]");
            return tokenResponse;

        } catch (Exception e) {
            logger.severe("[Authentication failed for user " + username + ". Error: " + e.getMessage() + "]");
            throw new RuntimeException("Authentication failed for user " + username, e);
        }
    }
    @CacheEvict(value = "AUTH_TOKEN", key = "#userId")
    public void logoutUser(String userId) {
        logger.info("[Logging out user " + userId + "]");
        try {
            UserResource userResource = keycloakAdminClient.realm(keycloakRealm).users().get(userId);
            userResource.logout();
            logger.info("[User " + userId + "has been logged out]");
        } catch (Exception e) {
            logger.warning("[Error logging out user " + e.getMessage() + "]");
        }
        ResponseEntity.ok().build();
    }

    @CacheEvict(value = "AUTH_TOKEN", key = "#userId")
    public void removeUser(String userId) {
        logger.info("[Removing user " + userId + "]");
        RealmResource realmResource = keycloakAdminClient.realm(keycloakRealm);
        UsersResource usersResource = realmResource.users();

        try {
            if (usersResource.get(userId).toRepresentation() != null) {
                usersResource.delete(userId);
                logger.info("[User removed " + userId + "]");
            } else {
                throw new RuntimeException("User not found");
            }
        } catch (Exception e) {
            logger.severe("[Failed to remove user " + userId + "]");
            throw new RuntimeException("[Failed to remove user " + userId + "]");
        }
    }
}



