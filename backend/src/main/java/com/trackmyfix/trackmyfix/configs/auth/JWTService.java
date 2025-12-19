package com.trackmyfix.trackmyfix.configs.auth;

import com.trackmyfix.trackmyfix.Dto.Request.TokenType;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
public class JWTService {

    private SecretKey secretkey = Jwts.SIG.HS256.key().build();
    private SecretKey refreshKey = Jwts.SIG.HS256.key().build();
    @Value(value = "900000")
    private String jwtExpiration;
    @Value(value = "604800000")
    private String jwtRefreshExpiration;

    public Map<String, String> generateToken(String gmail) {
        Map<String, Object> claims = new HashMap<>();
        Map<String, String> tokenInfo = new HashMap<>();
        Date issuedAt = new Date(System.currentTimeMillis());
        Date access_expiration = new Date(System.currentTimeMillis() + Integer.parseInt(jwtExpiration));
        Date refresh_expiration = new Date(System.currentTimeMillis() + Integer.parseInt(jwtRefreshExpiration));

        String access_token = Jwts.builder()
                .claims()
                .add(claims)
                .subject(gmail)
                .issuedAt(issuedAt)
                .expiration(access_expiration)
                .issuer("trackmyfix")
                .and()
                .signWith(getKey(TokenType.ACCESS))
                .compact();

        String refresh_token = Jwts.builder()
                .claims()
                .add(claims)
                .subject(gmail)
                .issuedAt(issuedAt)
                .expiration(refresh_expiration)
                .issuer("trackmyfix")
                .and()
                .signWith(getKey(TokenType.REFRESH))
                .compact();

        tokenInfo.put("refresh_token", refresh_token);
        tokenInfo.put("access_token", access_token);
        tokenInfo.put("token_type", "Bearer");
        tokenInfo.put("issued_at", issuedAt.toString());
        tokenInfo.put("expires_in", jwtExpiration);
        tokenInfo.put("refresh_expires", jwtRefreshExpiration);

        return tokenInfo;
    }

    private SecretKey getKey(TokenType type) {
        String base64 = Encoders.BASE64.encode(secretkey.getEncoded());
        System.out.println(base64);
        byte[] keyBytes = Decoders.BASE64.decode(base64);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token, TokenType type) {
        // extract the username from jwt token
        return extractClaim(token, type, Claims::getSubject);
    }

    private <T> T extractClaim(String token, TokenType type, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token, type);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token, TokenType type) {
        return Jwts.parser()
                .verifyWith(getKey(type))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, TokenType type, UserDetails userDetails) {
        final String userName = extractUsername(token, type);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token, type));
    }

    private boolean isTokenExpired(String token, TokenType type) {
        return extractExpiration(token, type).before(new Date());
    }

    private Date extractExpiration(String token, TokenType type) {
        return extractClaim(token, type, Claims::getExpiration);
    }

}
