package com.rohit.job_protal.security;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.rohit.job_protal.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
@Component
@Slf4j
public class JwtAuthUtil {

    @Value("${jwt.secretKey}")
    private String jwtSecret;

    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(User user) {

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("userId", user.getId())
                .claim("role", user.getRole().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (10 * 60 * 1000)))
                .signWith(getSecretKey())
                .compact();
    }
    
    public String getUserEmail(String token) {
    	try{
    		Claims claims = Jwts.parser()
    				.verifyWith(getSecretKey())
    				.build()
    				.parseSignedClaims(token)
    				.getPayload();
        	String role = claims.get("role",String.class);
    		return claims.getSubject();
    	}catch (ExpiredJwtException e) {
    		 log.warn("JWT expired");
		}catch(JwtException e) {
			 log.warn("JWT expired");
		}
    	return null;
    }
}
