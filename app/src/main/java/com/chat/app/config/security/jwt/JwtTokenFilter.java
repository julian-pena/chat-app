package com.chat.app.config.security.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.mongodb.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    public JwtTokenFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Verificar que el token no sea nulo y comience con "Bearer "
        if (jwtToken != null && jwtToken.startsWith("Bearer ")) {
            jwtToken = jwtToken.substring(7);  // Remover "Bearer "
            DecodedJWT decodedJWT = jwtUtils.validateToken(jwtToken);
            String userName = jwtUtils.getUserName(decodedJWT);
            String authority = jwtUtils.getSpecificClaim(decodedJWT, "authority").asString();  // Solo obtenemos un rol

            // Convertir autoridad a una colección (aunque solo hay un rol)
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority);
            Collection<GrantedAuthority> authorities = List.of(grantedAuthority);  // Lista con un solo rol

            // Crear y establecer el contexto de autenticación
            Authentication authentication = new UsernamePasswordAuthenticationToken(userName, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Pasar la solicitud al siguiente filtro
        filterChain.doFilter(request, response);
    }

}
