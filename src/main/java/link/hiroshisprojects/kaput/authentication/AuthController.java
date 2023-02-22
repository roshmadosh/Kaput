package link.hiroshisprojects.kaput.authentication;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import link.hiroshisprojects.kaput.user.User;
import link.hiroshisprojects.kaput.user.UserController;
import link.hiroshisprojects.kaput.user.UserException;
import link.hiroshisprojects.kaput.user.UserService;
import link.hiroshisprojects.kaput.user.UserValidationException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
public class AuthController {

	@Value("${admin.secret}")
	private String ADMIN_SECRET;

	private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

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

	/* Endpoint for making user an admin. */
	@PostMapping("/api/register/admin")
	public ResponseEntity<Map<String, Object>> authenticate(@RequestBody Map<String, String> body)
	throws UserException {

		String adminSecret = body.getOrDefault("secret", null);
		String userId = body.getOrDefault("userId", null);

		Map<String, Object> response = new HashMap<>();

		if (adminSecret != null && userId != null) {

			if (!adminSecret.equals(ADMIN_SECRET)) 
				throw new UserValidationException("adminSecret is not correct.");

			userService.makeAdmin(Long.parseLong(userId));

		} else {
				throw new UserValidationException("request body fields required ['secret', 'userId']");
		}

		response.put("success", true);
		response.put("message", "User with id " + userId + " granted ADMIN priviliges.");
		return ResponseEntity.ok().body(response);

	}
	
	/* For obtaining JWT token after successful registration. Include an Authorization header in request so that it goes through the
	 * JWT-generating filter. */
	@PostMapping("/api/authenticate")
	public void authenticate(Authentication authentication) throws UserException {
		LOGGER.debug(String.format("Attempt to authenticate user %s", authentication.toString()));	
	}
	
}
