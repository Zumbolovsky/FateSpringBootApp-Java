package br.com.zumbolovsky.fateapp.config.security

import br.com.zumbolovsky.fateapp.config.security.SecurityConfig.SecurityConstants.EXPIRATION_TIME
import br.com.zumbolovsky.fateapp.config.security.SecurityConfig.SecurityConstants.ISSUER
import br.com.zumbolovsky.fateapp.config.security.SecurityConfig.SecurityConstants.KEY
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import org.slf4j.LoggerFactory
import java.util.Date

class JwtToken {

    companion object {
        private val logger = LoggerFactory.getLogger(JwtToken::class.java)

        fun generateAccessToken(user: String): String =
            Jwts.builder()
                .setSubject(user)
                .setIssuer(ISSUER)
                .setIssuedAt(Date())
                .setExpiration(Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(Keys.hmacShaKeyFor(KEY.toByteArray()))
                .compact()

        fun getUsername(token: String): String =
            Jwts.parserBuilder()
                .setSigningKey(KEY.toByteArray())
                .build()
                .parseClaimsJws(token)
                .body
                .subject

        fun validate(token: String): Boolean =
            try {
                Jwts.parserBuilder()
                    .setSigningKey(KEY.toByteArray())
                    .build()
                    .parseClaimsJws(token).runCatching { }
                true
            } catch (ex: SignatureException) {
                logger.error("Invalid JWT signature - {}", ex.message)
                false
            } catch (ex: MalformedJwtException) {
                logger.error("Invalid JWT token - {}", ex.message)
                false
            } catch (ex: ExpiredJwtException) {
                logger.error("Expired JWT token - {}", ex.message)
                false
            } catch (ex: UnsupportedJwtException) {
                logger.error("Unsupported JWT token - {}", ex.message)
                false
            } catch (ex: IllegalArgumentException) {
                logger.error("JWT claims string is empty - {}", ex.message)
                false
            }
    }
}
