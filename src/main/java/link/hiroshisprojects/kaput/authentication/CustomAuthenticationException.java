package link.hiroshisprojects.kaput.authentication; import org.springframework.http.HttpStatus; import link.hiroshisprojects.kaput.exceptions.*; public class CustomAuthenticationException extends Exception implements CustomException { 

	public CustomAuthenticationException(String message) {
		super(message);
	}

	@Override
	public HttpStatus getStatusCode() {
		return HttpStatus.BAD_REQUEST;
	}

}
