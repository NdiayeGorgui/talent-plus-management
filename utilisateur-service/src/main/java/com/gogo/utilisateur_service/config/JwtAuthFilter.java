/*
package com.gogo.utilisateur_service.config;

import com.gogo.utilisateur_service.security.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Lire l’en-tête Authorization
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String prefix = "Bearer ";

        String token = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith(prefix)) {
            token = authHeader.substring(prefix.length());
            try {
                Claims claims = jwtUtil.getClaims(token);
                username = claims.getSubject();
            } catch (JwtException ex) {
                // Token invalide / expiré
                // On peut logger ou renvoyer une erreur
                // Ici, on ne fait rien, on laisse passer pour que la sécurité bloque l’accès
            }
        }

        // Si on a un username et que le contexte de sécurité n’est pas déjà authentifié
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            // On peut ajouter une validation supplémentaire (claims, roles, etc)
            if (userDetails != null && jwtUtil.getClaims(token) != null) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continuer la chaîne de filtres
        filterChain.doFilter(request, response);
    }
}
*/
