package link.hiroshisprojects.kaput.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public final class SecurityConstants {

	@Value("${jwt.secret}")
	private String jwtSecret;

	public String getJwtSecret() {
		return jwtSecret;
	}
}
