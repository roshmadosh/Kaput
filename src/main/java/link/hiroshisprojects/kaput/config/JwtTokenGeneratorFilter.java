package link.hiroshisprojects.kaput.config;

import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtTokenGeneratorFilter extends OncePerRequestFilter {

	private static final int EXPIRATION_IN_MINUTES = 30;
	private final String jwtSecret;

	public JwtTokenGeneratorFilter(String jwtSecret) {
		this.jwtSecret = jwtSecret;	
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Date date = new Date();
		if (authentication != null) {
			String jwt = Jwts.builder()
				.setIssuer("kaput")
				.setSubject("jwt token")
				.claim("userId", authentication.getName())
				.claim("authorities", authentication.getAuthorities().toString())
				.setIssuedAt(date)
				.setExpiration(new Date(date.getTime() + 1000* 60 * EXPIRATION_IN_MINUTES))
				.signWith(SignatureAlgorithm.HS256, jwtSecret).compact();

			Cookie cookie = new Cookie("Authorization", jwt);
			cookie.setMaxAge(60 * 60);
			cookie.setHttpOnly(true);

			response.addCookie(cookie);
		}
		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return !request.getServletPath().equals("/api/login");
	}

}
