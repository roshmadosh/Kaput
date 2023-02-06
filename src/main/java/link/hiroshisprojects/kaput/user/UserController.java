package link.hiroshisprojects.kaput.user;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public ResponseEntity<Iterable<User>> getUsers() {
		Iterable<User> users = userService.getAll();
		return ResponseEntity.ok().body(users); 
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable long id) throws UserNotFoundException {
		User user = userService.findUserById(id).orElseThrow(() -> new UserNotFoundException(String.format("User with ID %s not found.", id)));
		return ResponseEntity.ok().body(user);
	}

		
	@PostMapping
	public ResponseEntity<User> saveUser(@Valid @RequestBody User user) throws UserValidationException {
		try {
			User savedUser = userService.save(user);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedUser.getId())
				.toUri();

			return ResponseEntity.created(location).build();

		} catch (Exception e) {
			throw new UserValidationException("User field validation failed. " + e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public void deleteUserById(@PathVariable long id) {
		userService.deleteUserById(id);
	}


	
	
}
