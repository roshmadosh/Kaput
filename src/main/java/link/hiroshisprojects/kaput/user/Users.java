package link.hiroshisprojects.kaput.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

/* For having a link to the GET /users endpoint in our HAL-compliant response body. */ 
public class Users extends RepresentationModel<Users> {

	private List<User> users = new ArrayList<>();

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}
