package link.hiroshisprojects.kaput.user;

import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public ResponseEntity<Iterable<User>> getUsers() {
		Iterable<User> users = userService.getAll();
		return new ResponseEntity<Iterable<User>>(users, HttpStatus.OK);
	}
		
	@PostMapping
	public ResponseEntity<Map<String, Object>> saveUser(@RequestBody User user) {
		System.out.println(user);
		Map<String, Object> resp = new HashMap<>();
		try {
			User savedUser = userService.save(user);
			resp.put("message", "You've successfully saved user");
			resp.put("user", savedUser);
			
			return ResponseEntity.ok().body(resp);
		} catch (ConstraintViolationException cve) {
			resp.put("message", cve.getMessage());
			resp.put("user", user);
			return ResponseEntity.badRequest().body(resp);
		}
	}


	
	
}
