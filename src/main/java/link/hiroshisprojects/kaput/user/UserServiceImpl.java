package link.hiroshisprojects.kaput.user;

import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import link.hiroshisprojects.kaput.jobapplication.JobApplication;
import link.hiroshisprojects.kaput.jobapplication.JobApplicationDao;

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
	

	@Override
	public JobApplication addApplicationByUserId(long userId, JobApplication application) throws UserException {
		// throw an exception early if user ID not found
		User user = userDao.findById(userId).orElseThrow(() -> new UserNotFoundException("Cannot find user with ID " + userId));

		user.addJobApplication(application);
		List<JobApplication> applications = userDao.save(user).getJobApplications();

		// saved application should be the last one added to the list 
		return applications.get(applications.size() - 1);
	}
	
}
