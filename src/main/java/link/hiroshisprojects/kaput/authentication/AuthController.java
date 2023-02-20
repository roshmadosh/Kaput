package link.hiroshisprojects.kaput.authentication;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import link.hiroshisprojects.kaput.user.User;
import link.hiroshisprojects.kaput.user.UserController;
import link.hiroshisprojects.kaput.user.UserService;
import link.hiroshisprojects.kaput.user.UserValidationException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
public class AuthController {

	private UserService userService;

	public AuthController(UserService userService) {
		this.userService = userService;
	}

	/* Bad credentials will cause Spring Security to throw an Authentication Exception, which is handled
	 * by our ControllerAdvice. */
	@PostMapping("/api/login")
	public ResponseEntity<User> login(Authentication authentication) throws CustomAuthenticationException {
		if (authentication == null) 
			throw new AuthenticationHeaderNotFoundException();

		User user = userService.findUserById(Long.parseLong(authentication.getName())).get();		

		Link link = linkTo(methodOn(UserController.class).getUsers()).slash((user.getId())).withRel("user_details");
		Link appsLink = linkTo(methodOn(UserController.class).getJobApplications(user.getId())).withRel("user_applications");

		user.add(link, appsLink);

		return ResponseEntity.ok().body(user);
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
			ConstraintViolationException cve = (ConstraintViolationException) e.getCause();
			throw new UserValidationException(cve.getSQLException().getLocalizedMessage());
		}
	}
}
