package com.onlineakimbank.accountservice.config;

import com.onlineakimbank.accountservice.repository.AccountJpaRepository;
import com.onlineakimbank.accountservice.entity.Account;
import com.onlineakimbank.accountservice.entity.enums.State;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AccountStateFilter extends OncePerRequestFilter {

    private final AccountJpaRepository accountJpaRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getRequestURI();

        if (path.startsWith("/swagger") ||
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/actuator")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!path.contains("/account/")) {
            filterChain.doFilter(request, response);
            return;
        }

        String[] parts = path.split("/");
        String accountId = null;

        for (String p : parts) {
            if (p.matches("[0-9a-fA-F-]{36}")) { // тупой UUID детектор
                accountId = p;
                break;
            }
        }

        if (accountId == null) {
            filterChain.doFilter(request, response);
            return;
        }

        Account account = accountJpaRepository.findById(accountId).orElse(null);

        if (account == null) {
            response.sendError(404, "Account not found");
            return;
        }

        if (account.getState() != State.ACTIVE) {
            response.sendError(403, "Account is " + account.getState() + " and cannot perform operations");
            return;
        }

        filterChain.doFilter(request, response);
    }
}

