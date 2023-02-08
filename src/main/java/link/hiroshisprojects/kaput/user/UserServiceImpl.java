package link.hiroshisprojects.kaput.user;

import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	private UserDao userDao;

	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public User save(User user) throws ConstraintViolationException {
		String encrypted = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10));
		user.setPassword(encrypted);

		return userDao.save(user);
	}

	@Override
	public Iterable<User> getAll() {
		return userDao.findAll();
	}

	@Override
	public Optional<User> findUserById(long id) {
		return userDao.findById(id);
	}

	@Override
	public void deleteUserById(long id) {
		userDao.deleteById(id);
	}


	
}
