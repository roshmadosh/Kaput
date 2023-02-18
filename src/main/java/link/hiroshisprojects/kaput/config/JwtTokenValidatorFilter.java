package link.hiroshisprojects.kaput.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtTokenValidatorFilter extends OncePerRequestFilter {

	private final String jwtSecret;

	public JwtTokenValidatorFilter(String jwtSecret) {
		this.jwtSecret = jwtSecret;	
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String jwt = request.getHeader("Authorization");

		if (!jwt.isEmpty()) {
			try {
				Claims claims = Jwts.parser()
					.setSigningKey(jwtSecret)
					.parseClaimsJws(jwt)
					.getBody();

				String email = String.valueOf(claims.get("email"));
				String authorities = (String) claims.get("authorities");

				Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
			} catch (Exception e) {
				throw new BadCredentialsException("Invalid token received!");
			}
		}
		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean	shouldNotFilter(HttpServletRequest request) {
		return request.getServletPath().equals("/api/login");	
	}

}
