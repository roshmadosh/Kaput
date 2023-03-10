package link.hiroshisprojects.kaput.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import link.hiroshisprojects.kaput.user.User;
import link.hiroshisprojects.kaput.user.UserDao;

/**
 * Where our authentication implementation is defined, more-or-less.
 */
@Component
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

	private UserDao userDao;
	private PasswordEncoder passwordEncoder;

	public UsernamePasswordAuthenticationProvider(UserDao userDao, PasswordEncoder passwordEncoder) {
		this.userDao = userDao;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String email = authentication.getName();
		String password = authentication.getCredentials().toString();
		User user = userDao.findByEmail(email);
		
		if (user != null) {
			if (passwordEncoder.matches(password, user.getPassword())) {
				List<GrantedAuthority> authorities = new ArrayList<>();

				authorities.add(new SimpleGrantedAuthority("USER"));
				if (user.getIsAdmin()) {
					authorities.add(new SimpleGrantedAuthority("ADMIN"));
				}

				return new UsernamePasswordAuthenticationToken(String.valueOf(user.getId()), user.getPassword(), authorities);
			} else {
				throw new BadCredentialsException("Invalid password!");
			}
		} else {
			throw new BadCredentialsException("Account with email " + email + " not found.");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
