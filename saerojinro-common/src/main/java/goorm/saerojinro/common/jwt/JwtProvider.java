package goorm.saerojinro.common.jwt;

import static io.jsonwebtoken.Header.JWT_TYPE;
import static io.jsonwebtoken.Header.TYPE;
import static io.jsonwebtoken.SignatureAlgorithm.HS256;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import goorm.saerojinro.common.domain.BaseRole;
import goorm.saerojinro.common.jwt.exception.JwtExpiredException;
import goorm.saerojinro.common.jwt.exception.JwtInvalidException;
import goorm.saerojinro.common.jwt.exception.JwtMalformedException;
import goorm.saerojinro.common.jwt.exception.JwtSignatureInvalidException;
import goorm.saerojinro.common.jwt.exception.JwtUnsupportedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtProvider {
	private final JwtProperties jwtProperties;
	private final static String HEADER_AUTHORIZATION = "Authorization";
	private final static String TOKEN_PREFIX = "Bearer ";

	public String generateAccessToken(String email, BaseRole role) {
		Date now = new Date();
		return makeToken(new Date(now.getTime() + Duration.ofHours(1).toMillis()), email, role);
	}

	private String makeToken(Date expiry, String email, BaseRole role) {
		Date now = new Date();

		return Jwts.builder()
			.setHeaderParam(TYPE,JWT_TYPE)
			.setIssuer(jwtProperties.getIssuer())
			.setIssuedAt(now)
			.setExpiration(expiry)
			.setSubject(email)
			.claim("role", role.name())
			.signWith(HS256, jwtProperties.getSecretKey())
			.compact();
	}

	public boolean validateToken(String token) {
		if (token == null) {
			return false;
		}

		try {
			Jwts.parser()
				.setSigningKey(jwtProperties.getSecretKey())
				.parseClaimsJws(token);
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
			case "ATTENDEE" -> Collections.singleton(new SimpleGrantedAuthority("ROLE_ATTENDEE"));
			default -> throw new JwtInvalidException();
		};
	}

	public String extractAccessToken(HttpServletRequest request) {
		String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);
		if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
			return authorizationHeader.substring(TOKEN_PREFIX.length());
		}
		return null;
	}

	private Claims getClaims(String token) {
		return Jwts.parser()
			.setSigningKey(jwtProperties.getSecretKey())
			.parseClaimsJws(token)
			.getBody();
	}

	public Long getRemainingExpiration(String accessToken) {
		Claims claims = getClaims(accessToken);
		Date expiration = claims.getExpiration();
		return (expiration.getTime() - System.currentTimeMillis()) / 1000;
	}
}
