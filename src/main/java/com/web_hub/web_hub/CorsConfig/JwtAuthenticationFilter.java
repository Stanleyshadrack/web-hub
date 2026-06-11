package com.web_hub.web_hub.CorsConfig;

import com.web_hub.web_hub.jwt.JwtService;
import com.web_hub.web_hub.user.model.User;
import io.jsonwebtoken.ExpiredJwtException; // Ensure you have this import
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);

        try {
            username = jwtService.extractUsername(jwt);
        } catch (ExpiredJwtException e) {
            // 👇 NEW LOGIC: Catch expired tokens specifically and return a 401 response
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"JWT token has expired. Please log in again.\"}");
            return; // Halt the request right here
        } catch (Exception e) {
            // log other token parsing errors if needed
            filterChain.doFilter(request, response);
            return;
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtService.isTokenValid(jwt, userDetails)) {

                boolean isTokenValid = true;

                if (userDetails instanceof User user) {
                    if (user.getLastLogoutDate() != null) {
                        Date issueDate = jwtService.extractIssuedAt(jwt);

                        if (issueDate != null && issueDate.toInstant().isBefore(user.getLastLogoutDate())) {
                            isTokenValid = false;
                        }
                    }
                }

                if (isTokenValid) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    // Optional: Indicate if the token was invalidated due to a previous logout
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"error\": \"Token invalidated due to recent logout.\"}");
                    return;
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}