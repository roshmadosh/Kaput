package link.hiroshisprojects.kaput.user;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolationException;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import link.hiroshisprojects.kaput.jobApplication.JobApplication;
import link.hiroshisprojects.kaput.jobApplication.JobApplicationDao;

@Service
public class UserServiceImpl implements UserService {

	private UserDao userDao;
	private JobApplicationDao applicationsDao;

	public UserServiceImpl(UserDao userDao, JobApplicationDao applicationsDao) {
		this.userDao = userDao;
		this.applicationsDao = applicationsDao;
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

	@Override
	public List<JobApplication> getApplicationsByUserId(long userId) {
		return applicationsDao.findByUserId(userId); 
	}

	@Override
	public User addApplicationByUserId(long userId, JobApplication application) throws UserException {
		User user = userDao.findById(userId).orElseThrow(() -> new UserNotFoundException("Cannot find user with ID " + userId));
		user.addJobApplication(application);
		return userDao.save(user);
	}

	
}
