package com.project.fitness.security;

import java.security.Key;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class jwtUtils {
    
    private String jwtSecret="YS1zdHJpbmctc2VjcmV0LWF0LWxlYXN0LTI1Ni1iaXRzLWxvbmc=";
    private int jwtExpirationMs=172800000;

    public String getJwtFromHeader(HttpServletRequest request){
        String bearerToken=request.getHeader("Authorization");
        if (bearerToken!=null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }
    
    public String generateToken(String id , String role){

        return Jwts.builder()
            .subject(id)
            .claim("roles", role)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
            .signWith(key())
            .compact();
    }

    public boolean validateJwtToken(String jwtToken){
       try{
           Jwts.parser()
                   .verifyWith((SecretKey) key())      // ✅ verifyWith
                   .build()
                   .parseSignedClaims(jwtToken);
       }catch (Exception e){
           e.printStackTrace();
       }
        return true;
    }
    private Key key(){

        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
    public String getUserIdFromJwtToken(String token) {

        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
   public String getRoleFromJwtToken(String token) {

    Claims claims = Jwts.parser()
            .verifyWith((SecretKey) key())
            .build()
            .parseSignedClaims(token)
            .getPayload();

    return claims.get("roles", String.class);
}
}
