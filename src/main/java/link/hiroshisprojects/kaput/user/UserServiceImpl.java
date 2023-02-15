package link.hiroshisprojects.kaput.user;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.data.jpa.repository.support.QueryHints.NoHints;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import link.hiroshisprojects.kaput.jobapplication.JobApplication;

@Service
public class UserServiceImpl implements UserService {

	private UserDao userDao;
	private PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
		this.userDao = userDao;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public User save(User user) throws ConstraintViolationException {
		String encrypted = passwordEncoder.encode(user.getPassword());
		user.setPassword(encrypted);

		return userDao.save(user);
	}

	@Override
	public Iterable<User> getAll() {
		return userDao.findAll();
	}

	@Override
	@PreAuthorize("T(Long).parseLong(authentication.principal) == #id")
	public Optional<User> findUserById(long id) {
		return userDao.findById(id);
	}

	@Override
	@PreAuthorize("T(Long).parseLong(authentication.principal) == #id")
	public void deleteUserById(long id) {
		userDao.deleteById(id);
	}
	

	@Override
	@PreAuthorize("T(Long).parseLong(authentication.principal) == #userId")
	public JobApplication addApplicationByUserId(long userId, JobApplication application) throws UserException {
		User user = userDao.findById(userId).orElseThrow(() -> new UserNotFoundException("Cannot find user with ID " + userId));
		user.addJobApplication(application);

		List<JobApplication> applications = userDao.save(user).getJobApplications();

		// saved application should be the last one added to the list 
		return applications.get(applications.size() - 1);
	}

	@Override
	@PreAuthorize("T(Long).parseLong(authentication.principal) == #userId")
	public User updateUserById(long userId, Map<String, String> updatedFields) throws UserException {
		
		User user = userDao.findById(userId).orElseThrow(() -> new UserNotFoundException("Cannot find user with ID " + userId));

		user.updateUser(updatedFields);	

		User updated = userDao.save(user);

		return updated;
	}

	@Override
	public User findByEmail(String email) {
		return userDao.findByEmail(email);	
	}
	
}
