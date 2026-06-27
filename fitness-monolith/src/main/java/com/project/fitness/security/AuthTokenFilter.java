package com.project.fitness.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
    public class AuthTokenFilter extends OncePerRequestFilter {

      
       private final jwtUtils jwtutils;

    

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("filter called");
        try {
            String jwt=tokennikalo(request);
            if (jwt!=null && jwtutils.validateJwtToken (jwt)){
                String userId = jwtutils.getUserIdFromJwtToken(jwt);
                String role = jwtutils.getRoleFromJwtToken(jwt);

               List<GrantedAuthority> authorities =
        List.of(new SimpleGrantedAuthority(role));
                // UserDetails userDetails =
                //         userDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userId,
                                null,
                                authorities
                        );
                authentication.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );
                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        filterChain.doFilter(request, response);
    }
    private String tokennikalo(HttpServletRequest request){
        String jwt=jwtutils.getJwtFromHeader(request);
        return jwt;
    }
}
