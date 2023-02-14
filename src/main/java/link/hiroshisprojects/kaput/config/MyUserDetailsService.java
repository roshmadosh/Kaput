package link.hiroshisprojects.kaput.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import link.hiroshisprojects.kaput.user.User;
import link.hiroshisprojects.kaput.user.UserDao;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final User user = userDao.findByEmail(username);

		if (user == null) {
			throw new UsernameNotFoundException(username);
		}

		UserDetails userDetails = org.springframework.security.core.userdetails.User
			.withUsername(username)
			.password(user.getPassword())
			.authorities("USER")
			.build();

		return userDetails;
	}
}
