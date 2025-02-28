package goorm.saerojinro.common.auth.jwt.application;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import goorm.saerojinro.common.auth.domain.Role;
import goorm.saerojinro.common.auth.jwt.JwtProperties;
import goorm.saerojinro.common.auth.jwt.exception.JwtExpiredException;
import goorm.saerojinro.common.auth.jwt.exception.JwtInvalidException;
import goorm.saerojinro.common.auth.jwt.exception.JwtMalformedException;
import goorm.saerojinro.common.auth.jwt.exception.JwtSignatureInvalidException;
import goorm.saerojinro.common.auth.jwt.exception.JwtUnsupportedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtProvider {
    private final JwtProperties jwtProperties;

    private final Header header = Jwts.header().type("JWT").build();

    public String generateAccessToken(String userId, Role role, Duration duration) {
        Date now = new Date();
        return makeToken(new Date(now.getTime() + duration.toMillis()), userId, role);
    }

    public String generateAccessToken(String userId, Role role) {
        Date now = new Date();
        return makeToken(new Date(now.getTime() + Duration.ofMinutes(30).toMillis()), userId, role);
    }

    public String generateRefreshToken(String userId, Role role) {
        Date now = new Date();
        return makeToken(new Date(now.getTime() + Duration.ofDays(1).toMillis()), userId, role);
    }

    private String makeToken(Date expiry, String userId, Role role) {
        Date now = new Date();

        return Jwts.builder()
            .header().add(header).and()
            .issuer(jwtProperties.getIssuer())
            .issuedAt(now)
            .expiration(expiry)
            .subject(userId)
            .claim("role", role.name())
            .signWith(jwtProperties.getSecretKey())
            .compact();
    }

    public boolean validateToken(String token) {
        if (token == null) {
            return false;
        }

        try {
            Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getEncoded()))
                .build()
                .parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new JwtExpiredException();
        } catch (UnsupportedJwtException e) {
            throw new JwtUnsupportedException();
        } catch (MalformedJwtException e) {
            throw new JwtMalformedException();
        } catch (SignatureException e) {
            throw new JwtSignatureInvalidException();
        } catch (Exception e) {
            throw new JwtInvalidException();
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities = getRoles(claims);

        return new UsernamePasswordAuthenticationToken(
                new org.springframework.security.core.userdetails.User(
                        claims.getSubject(),
                        "",
                        authorities
                ), token, authorities
        );
    }

    public Set<SimpleGrantedAuthority> getRoles(Claims claims) {
        String role = claims.get("role", String.class);

		return switch (role) {
			case "ADMIN" -> Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"));
			case "SPEAKER" -> Collections.singleton(new SimpleGrantedAuthority("ROLE_SPEAKER"));
			case "ATTENDEE" -> Collections.singleton(new SimpleGrantedAuthority("ATTENDEE"));
			default -> throw new JwtSignatureInvalidException();
		};
    }

    public String getUserId(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
            .verifyWith(Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getEncoded()))
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }
}
