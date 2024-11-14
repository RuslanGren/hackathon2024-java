package com.ua.hackathon2024java.config;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtTokenUtils jwtTokenUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = null;
        String username = null;

        // Перевіряємо заголовок Authorization
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7); // Отримуємо токен без "Bearer "
        } else {
            // Якщо не знайшли в заголовку, шукаємо в cookie
            if (request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if ("JWT".equals(cookie.getName())) {
                        jwt = cookie.getValue();
                        break;
                    }
                }
            }
        }

        // Якщо токен є, проводимо перевірку
        if (jwt != null) {
            try {
                username = jwtTokenUtils.getEmail(jwt);  // Отримуємо email користувача з токена
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    var auth = new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            jwtTokenUtils.getRoles(jwt).stream()
                                    .map(SimpleGrantedAuthority::new)
                                    .collect(Collectors.toList())
                    );
                    SecurityContextHolder.getContext().setAuthentication(auth);  // Налаштовуємо аутентифікацію
                }
            } catch (ExpiredJwtException e) {
                log.debug("Token has expired", e);
            } catch (Exception e) {
                log.debug("Invalid token", e);
            }
        }

        // Продовжуємо ланцюг фільтрів
        filterChain.doFilter(request, response);
    }
}