package link.hiroshisprojects.kaput.config;

import java.util.Arrays;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import link.hiroshisprojects.kaput.config.JwtTokenGeneratorFilter;
import link.hiroshisprojects.kaput.config.JwtTokenValidatorFilter;
import link.hiroshisprojects.kaput.config.SecurityConstants;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSecurityConfig {

	private static final String[] PUBLIC_ENDPOINTS = {"/api/login", "/api/register/**", "/api/authenticate"};

	@Autowired
	private FilterChainExceptionHandler filterChainExceptionHandler;

	@Autowired
	private SecurityConstants CONSTANTS;

	@Autowired
	@Qualifier("restAuthenticationEntryPoint")
	private AuthenticationEntryPoint authenticationEntryPoint;

	@Bean
	@Profile("dev")
	SecurityFilterChain securityFilterChainDev(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and().cors().configurationSource(new CorsConfigurationSource() {
				@Override
				public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
					CorsConfiguration config = new CorsConfiguration();
					config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
					config.setAllowedMethods(Collections.singletonList("*"));
					config.setAllowCredentials(true);
					config.setAllowedHeaders(Collections.singletonList("*"));
					config.setMaxAge(3600L);

					return config;
				}
		}).and().authorizeRequests()
				.antMatchers(PUBLIC_ENDPOINTS).permitAll()
				.antMatchers("/api/users/**").authenticated() // checks if Authentication in SecurityContext is not null
			.and().httpBasic()
				.authenticationEntryPoint(authenticationEntryPoint) // for custom exception handling
			.and().csrf()
				.ignoringAntMatchers(PUBLIC_ENDPOINTS)
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // a new token is sent on every request
			.and()
				.addFilterBefore(filterChainExceptionHandler, LogoutFilter.class) // for handling exceptions thrown from filters
				.addFilterBefore(new JwtTokenValidatorFilter(CONSTANTS.getJwtSecret()), BasicAuthenticationFilter.class)
				.addFilterAfter(new JwtTokenGeneratorFilter(CONSTANTS.getJwtSecret()), BasicAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	@Profile({ "prod" })
	SecurityFilterChain securityFilterChainProd(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.anyRequest().authenticated()
			.and().formLogin()
			.and().httpBasic();

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}


}
