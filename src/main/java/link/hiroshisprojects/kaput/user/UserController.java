package link.hiroshisprojects.kaput.user;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping(value = "/api/users", produces = "application/hal+json")
public class UserController {

	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public Users getUsers() {
		Users resp = new Users();

		// DAO access
		Iterable<User> users = userService.getAll();

		// add links to representation models (HATEAOS)
		for (User user: users) {
			Link link = linkTo(this.getClass()).slash(user.getId()).withSelfRel();
			user.add(link);

			// add User rep model to Users rep model
			resp.getUsers().add(user);
		}

		// link for the /users endpoint itself
		Link selfLink = linkTo(methodOn(this.getClass()).getUsers()).withSelfRel();

		resp.add(selfLink);

		return resp;
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