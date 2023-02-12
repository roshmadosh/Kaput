package link.hiroshisprojects.kaput.jobapplication;

import java.time.LocalDate;

import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

@Component
public class JobApplicationDTO {

	@Size(max = 30, message = "Job title has a 30-character maximum.")
	private String jobTitle;

	@Size(max = 30, message = "Company name has a 30-character maximum.")
	private String companyName;

  @PastOrPresent(message = "Invalid date given for field 'dateApplied'.")
  @JsonFormat(pattern = "MM/dd/yyyy")
	private LocalDate dateApplied;

	private ApplicationStatus status;

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public LocalDate getDateApplied() {
		return dateApplied;
	}

	public void setDateApplied(LocalDate dateApplied) {
		this.dateApplied = dateApplied;
	}

	public ApplicationStatus getStatus() {
		return status;
	}

	public void setStatus(ApplicationStatus status) {
		this.status = status;
	}



}
