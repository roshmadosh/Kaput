package link.hiroshisprojects.kaput.jobapplication;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JobApplicationTests {
	private JobApplication sut;


	@BeforeEach() 
	public void init() {
		sut = new JobApplication("test", "test", null);
		sut.setId(1);
	}	


	@Test
	@DisplayName("update() updates fields.")
	public void updateValid() {
		// Arrange
		Object[] updatedValues = {"updated job title", "updated company name", LocalDate.now(), ApplicationStatus.ACCEPTED};
		JobApplicationDTO dto = new JobApplicationDTO();
		dto.setJobTitle(updatedValues[0].toString());
		dto.setCompanyName(updatedValues[1].toString());
		dto.setDateApplied((LocalDate) updatedValues[2]);
		dto.setStatus((ApplicationStatus) updatedValues[3]);

		long originalId = sut.getId();
	
		// Act
		sut.update(dto);

		// Assert
		assertAll(
			() -> assertEquals(originalId, sut.getId()),
			() -> assertEquals(updatedValues[0], sut.getJobTitle()),
			() -> assertEquals(updatedValues[1], sut.getCompanyName()),
			() -> assertEquals(updatedValues[2], sut.getDateApplied()),
			() -> assertEquals(updatedValues[3], sut.getStatus())
		);
	}
}
