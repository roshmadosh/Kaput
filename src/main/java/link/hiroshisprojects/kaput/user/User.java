package link.hiroshisprojects.kaput.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.hateoas.RepresentationModel;

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

	public User(String email, String firstName, String lastName, String password) {
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
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

	public User() {
	}

}
