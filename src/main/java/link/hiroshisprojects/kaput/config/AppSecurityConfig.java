package link.hiroshisprojects.kaput.config;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSecurityConfig {

	private static final String[] PUBLIC_ENDPOINTS = {"/api/login", "/api/register"};

	@Bean
	@Profile("dev")
	SecurityFilterChain securityFilterChainDev(HttpSecurity http) throws Exception {
		http.cors().configurationSource(new CorsConfigurationSource() {
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
				.antMatchers("/api/users", "/api/users/").hasRole("ADMIN")
				.antMatchers("/api/users/**").authenticated()
			.and().csrf()
				.ignoringAntMatchers(PUBLIC_ENDPOINTS)
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
			.and().httpBasic();

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
