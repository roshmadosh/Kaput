package link.hiroshisprojects.kaput.user;

import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import link.hiroshisprojects.kaput.jobApplication.JobApplication;

public interface UserService {
	public User save(User user) throws ConstraintViolationException;

	public Iterable<User> getAll();

	public Optional<User> findUserById(long id);

	public void deleteUserById(long id);

	public List<JobApplication> getApplicationsByUserId(long userId); 

	public User addApplicationByUserId(long userId, JobApplication application) throws UserException;

}
