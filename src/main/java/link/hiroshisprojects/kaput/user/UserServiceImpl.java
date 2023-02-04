package link.hiroshisprojects.kaput.user;

import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	private UserDao userDao;

	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public User save(User user) throws ConstraintViolationException {
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
	
}
