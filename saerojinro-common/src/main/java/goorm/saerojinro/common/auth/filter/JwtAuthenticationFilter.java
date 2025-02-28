package goorm.saerojinro.common.auth.filter;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import goorm.saerojinro.common.auth.jwt.application.JwtProvider;
import goorm.saerojinro.common.exception.CustomException;
import goorm.saerojinro.common.exception.ExceptionResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final JwtProvider jwtProvider;
	private final static String HEADER_AUTHORIZATION = "Authorization";
	private final static String TOKEN_PREFIX = "Bearer ";

	@Override
	protected void doFilterInternal(
		HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
	) throws ServletException, IOException {
		try {
			String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);
			String token = getAccessToken(authorizationHeader);
			if (jwtProvider.validateToken(token)) {
				Authentication authentication = jwtProvider.getAuthentication(token);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			filterChain.doFilter(request, response);
		} catch (CustomException e) {
			setErrorResponse(e, response);
		}
	}

	private void setErrorResponse(CustomException exception, HttpServletResponse response) throws IOException {
		ExceptionResponse exceptionResponse = ExceptionResponse.from(exception);

		response.setStatus(exceptionResponse.status().value());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		String jsonResponse = String.format(
			"{\"code\": \"%s\"}",
			exceptionResponse.code()
		);

		response.getWriter().write(jsonResponse);
	}

	private String getAccessToken(String authorizationHeader) {
		if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
			return authorizationHeader.substring(TOKEN_PREFIX.length());
		}
		return null;
	}
}