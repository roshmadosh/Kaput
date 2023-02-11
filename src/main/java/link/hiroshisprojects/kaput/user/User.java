package link.hiroshisprojects.kaput.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnore;

import link.hiroshisprojects.kaput.jobapplication.JobApplication;

@Entity
@Table(name="users")
public class User extends RepresentationModel<User>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "email", unique = true)
	@Email(message = "Please provide a valid email address!")
	@NotEmpty(message = "Email cannot be empty!")
	private String email;
	
	@Size(min = 2, max = 55)
	@NotEmpty(message = "First name cannot be empty!")
	private String firstName;

	@Size(min = 2, max = 55)
	@NotEmpty(message = "Last name cannot be empty!")
	private String lastName;

	@NotEmpty(message = "You must provide a password!")
	private String password;

	@OneToMany(
		orphanRemoval = true,
		mappedBy = "user",
		cascade = { CascadeType.MERGE, CascadeType.REMOVE })
	@JsonIgnore
	private List<JobApplication> jobApplications;

	public User() {
	}

	public User(String email, String firstName, String lastName, String password) {
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.jobApplications = new ArrayList<>();
	}

	public void	addJobApplication(JobApplication application) throws UserValidationException {
		application.setUser(this);

		if (this.getJobApplications().contains(application)) {
			throw new UserValidationException("Duplicate application with job title '" + application.getJobTitle() +
				"' for company '" + application.getCompanyName() + 
				"' on applied date " + application.getDateApplied() +	
				" found for user with ID " + this.getId());
		}

		this.jobApplications.add(application);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<JobApplication> getJobApplications() {
		return jobApplications;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id != other.id)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}
}
