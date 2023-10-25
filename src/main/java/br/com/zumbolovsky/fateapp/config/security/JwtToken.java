package br.com.zumbolovsky.fateapp.config.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import static br.com.zumbolovsky.fateapp.config.security.SecurityConfig.SecurityConstants.*;
import static br.com.zumbolovsky.fateapp.config.security.SecurityConfig.SecurityConstants.EXPIRATION_TIME;
import static br.com.zumbolovsky.fateapp.config.security.SecurityConfig.SecurityConstants.KEY;

public class JwtToken {

    private static final Logger logger = LoggerFactory.getLogger(JwtToken.class);

    public static String generateAccessToken(String user) {
        return Jwts.builder()
                .setSubject(user)
                .setIssuer(ISSUER)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(Keys.hmacShaKeyFor(KEY.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    public static String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(KEY.getBytes(StandardCharsets.UTF_8))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public static Boolean validate(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(KEY.getBytes(StandardCharsets.UTF_8))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature - {}", ex.getMessage());
            return false;
        } catch(MalformedJwtException ex) {
            logger.error("Invalid JWT token - {}", ex.getMessage());
            return false;
        } catch(ExpiredJwtException ex) {
            logger.error("Expired JWT token - {}", ex.getMessage());
            return false;
        } catch(UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token - {}", ex.getMessage());
            return false;
        } catch(IllegalArgumentException ex) {
            logger.error("JWT claims string is empty - {}", ex.getMessage());
            return false;
        }
    }
}
