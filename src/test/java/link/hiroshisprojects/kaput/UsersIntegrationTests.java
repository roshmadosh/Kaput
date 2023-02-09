package link.hiroshisprojects.kaput;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import link.hiroshisprojects.kaput.user.User;
import link.hiroshisprojects.kaput.user.UserService;

@SpringBootTest
@ActiveProfiles("test")
class UsersIntegrationTests {

	@Autowired
	UserService userService;

	@Test
	@DisplayName("H2 initialization successful, user found")
	void dbInit() {
		List<User> users = new ArrayList<>();
		Iterable<User> query = userService.getAll();	

		query.forEach(users::add);

		assertEquals(1, users.size());

		User queriedUser = users.get(0);

		assertEquals("test_first", queriedUser.getFirstName());
	}

}
