package link.hiroshisprojects.kaput.authentication;

public class AuthenticationHeaderNotFoundException extends CustomAuthenticationException {
	public AuthenticationHeaderNotFoundException() {
		super("Authorization header required.");
	}
}
