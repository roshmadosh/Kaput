package link.hiroshisprojects.kaput.user;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class AuthController {

	private UserService userService;

	public AuthController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/api/login")
	public ResponseEntity<User> login(Authentication authentication) {
		User user = userService.findUserById(Long.parseLong(authentication.getName())).get();		
		if (user != null) {		
			return ResponseEntity.ok().body(user);
		}	
		return ResponseEntity.badRequest().body(user);
	}

	@PostMapping("/api/register")
	public ResponseEntity<User> register(@Valid @RequestBody User user) throws UserValidationException {
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
}
