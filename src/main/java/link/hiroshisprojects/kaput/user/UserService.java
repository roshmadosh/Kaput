package link.hiroshisprojects.kaput.user;

import java.util.Map;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import link.hiroshisprojects.kaput.jobapplication.JobApplication;

public interface UserService {
	public User save(User user) throws ConstraintViolationException;

	public Iterable<User> getAll();
	public Optional<User> findUserById(long id);

	public void deleteUserById(long id);

	public JobApplication addApplicationByUserId(long userId, JobApplication application) throws UserException;

	public User updateUserById(long userId, Map<String, String> updatedFields) throws UserException;

}
