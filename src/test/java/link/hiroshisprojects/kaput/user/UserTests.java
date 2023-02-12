package link.hiroshisprojects.kaput.user;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTests {

	private User sut;
	private String[] updateValues;

	@BeforeEach
	public void init() {
		this.sut = new User("test@email.com", "testFirst", "testLast", "notTested"); 
		this.updateValues = new String[] {"updated@email.com", "updatedFirst", "updatedLast"};
	}

	@Test
	@DisplayName("updateUser() updates with valid keys") 
	public void updateValid() throws UserException {
		// Arrange
		String[] validKeys = {"email", "firstName", "lastName"};
		Map<String, String> updateMap = new HashMap<>();

		for (int i=0; i<updateValues.length; i++) {
			updateMap.put(validKeys[i], updateValues[i]);
		}

		// Act
		sut.updateUser(updateMap);

		// Assert
		User expected = new User(updateValues[0], updateValues[1], updateValues[2], "notTested");
		assertEquals(expected, sut);
	}

	@Test
	@DisplayName("updateUser() throws Exception if given invalid keys.") 
	public void updateInvalid() throws UserException {
		// Arrange
		String[] invalidKeys = {"invalid"};
		Map<String, String> updateMap = new HashMap<>();

		for (int i=0; i<invalidKeys.length; i++) {
			updateMap.put(invalidKeys[i], "not tested");
		}

		// Act + Assert
		assertThrows(UserException.class, () -> {
			sut.updateUser(updateMap);
		});
	}

	@Disabled // Setter validation must be done manually. Spring only validates at controller or dao level 
	@Test
	@DisplayName("updatedUser() throws Exception if given invalid values.")
	public void updateUserInvalidValues() throws UserException {
		// Arrange
		Map<String, String> updateMap = new HashMap<>(Map.of("email", "invalidEmail"));

		// Act + Assert
		assertThrows(UserException.class, () -> {
			sut.updateUser(updateMap);
		});
	}
}
