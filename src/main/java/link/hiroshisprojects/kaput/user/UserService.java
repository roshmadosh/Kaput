package link.hiroshisprojects.kaput.user;

import java.util.Optional;

import javax.validation.ConstraintViolationException;

public interface UserService {
	public User save(User userDto) throws ConstraintViolationException;

	public Iterable<User> getAll();

	public Optional<User> findUserById(long id);

}
