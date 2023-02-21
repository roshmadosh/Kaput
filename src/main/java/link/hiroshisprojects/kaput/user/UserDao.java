package link.hiroshisprojects.kaput.user;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<User, Long> {
	User findByEmail(String email);

	@Modifying
	@Query("update User user set user.isAdmin = true where user.id = ?1")
	void makeAdmin(long userId);

}
