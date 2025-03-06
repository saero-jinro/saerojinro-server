package goorm.saerojinro.common.filter;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import goorm.saerojinro.common.exception.CustomException;
import goorm.saerojinro.common.exception.ExceptionResponse;
import goorm.saerojinro.common.jwt.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final JwtProvider jwtProvider;

	@Override
	protected void doFilterInternal(
		HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
	) throws ServletException, IOException {
		try {
			String token = jwtProvider.extractAccessToken(request);
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


}