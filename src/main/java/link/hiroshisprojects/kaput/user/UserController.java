package link.hiroshisprojects.kaput.user;

import java.net.URI;
import java.util.List;

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

import link.hiroshisprojects.kaput.jobapplication.JobApplicationService;
import link.hiroshisprojects.kaput.jobapplication.JobApplicationNotFoundException;
import link.hiroshisprojects.kaput.jobapplication.JobApplicationException;
import link.hiroshisprojects.kaput.jobapplication.JobApplication;
import link.hiroshisprojects.kaput.jobapplication.JobApplications;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping(value = "/api/users", produces = "application/hal+json")
public class UserController {

	private UserService userService;
	private JobApplicationService appService;

	public UserController(UserService userService, JobApplicationService appService) {
		this.userService = userService;
		this.appService = appService;
	}

	@GetMapping
	public Users getUsers() {
		Users resp = new Users();

		// DAO access
		Iterable<User> users = userService.getAll();

		// add links to representation models (HATEOAS)
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

	@GetMapping(value = "/{id}", produces = "application/hal+json")
	public ResponseEntity<User> getUserById(@PathVariable long id) throws UserNotFoundException {
		User user = userService.findUserById(id).orElseThrow(() -> new UserNotFoundException(String.format("User with ID %s not found.", id)));
		Link link = linkTo(this.getClass()).slash(id).withSelfRel();
		user.add(link);

		Link appsLink = linkTo(methodOn(this.getClass()).getJobApplications(id)).withRel("job_applications");
		user.add(appsLink);

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

	/* Not sure how to incorporate HATEOAS here b/c JPA's delete method doesn't return the object. */
	@DeleteMapping(value = "/{id}")
	public void deleteUserById(@PathVariable long id) {
		userService.deleteUserById(id);
		
	}

	@GetMapping(value= "/{userId}/applications")
	public JobApplications getJobApplications(@PathVariable long userId) {

		JobApplications resp = new JobApplications();

		List<JobApplication> applications = appService.findJobApplicationsByUserId(userId);

		// add links to representation models (HATEOAS)
		for (JobApplication application: applications) {
			Link link = linkTo(methodOn(this.getClass()).getJobApplications(userId)).slash(application.getId()).withSelfRel();
			application.add(link);

			resp.getApplications().add(application);
		}

		// link for the endpoint itself
		Link selfLink = linkTo(methodOn(this.getClass()).getJobApplications(userId)).withSelfRel();

		resp.add(selfLink);

		return resp;
	
	}

	@GetMapping("/{userId}/applications/{appId}")
	public JobApplication getApplicationByUserId(@PathVariable long userId, @PathVariable long appId) throws JobApplicationNotFoundException {
		String errorMessage = "Job Application with ID " + appId + " not found.";
		JobApplication application = appService.findJobApplicationById(appId).orElseThrow(() -> new JobApplicationNotFoundException(errorMessage));
		
		Link selfLink = linkTo(methodOn(this.getClass()).getApplicationByUserId(userId, appId)).withSelfRel();
		Link allLink = linkTo(methodOn(this.getClass()).getJobApplications(userId)).withRel("all");

		application.add(selfLink, allLink);

		return application;
	}

	@PostMapping("/{userId}/applications")
	public JobApplication saveApplicationForUser(@PathVariable long userId, @Valid @RequestBody JobApplication application) throws UserException, JobApplicationException {
		JobApplication savedApp = userService.addApplicationByUserId(userId, application);	

		Link createdLink = linkTo(methodOn(this.getClass()).getApplicationByUserId(userId, savedApp.getId())).withRel("created");
		Link allLink = linkTo(methodOn(this.getClass()).getJobApplications(userId)).withRel("all");

		savedApp.add(createdLink, allLink);

		return savedApp;
		
	}
	
	
}
