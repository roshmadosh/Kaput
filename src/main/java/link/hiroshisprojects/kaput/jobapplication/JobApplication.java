package link.hiroshisprojects.kaput.jobapplication;

import java.time.LocalDate;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

import link.hiroshisprojects.kaput.user.User;

@Entity
@Table(name="job_applications")
public class JobApplication extends RepresentationModel<JobApplication> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne()
	private User user;

	@Column(name="job_title", nullable = false)
	@Size(max = 30)
	@NotEmpty(message = "Field 'jobTitle' is required!")
	private String jobTitle;

	@Column(name = "company_name", nullable = false)
	@Size(min = 1, max = 55)
	@NotEmpty(message = "Field 'companyName' is required!")
	private String companyName;

	@Column(name="date_applied", nullable = false)
	@PastOrPresent(message = "Invalid date given for field 'dateApplied'.")
  @JsonFormat(pattern = "MM/dd/yyyy")
	@NotNull(message = "Field 'dateApplied' must be provided, format is MM/dd/yyyy.")
	@Basic
	private LocalDate dateApplied;

	@Column(name="status", nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private ApplicationStatus status;

	public JobApplication() {}

	@JsonCreator
	public JobApplication(String jobTitle, String companyName, LocalDate dateApplied) {
		this.jobTitle = jobTitle;
		this.dateApplied = dateApplied;
		this.companyName = companyName;
		this.status = ApplicationStatus.NOT_RESPONDED; 
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public LocalDate getDateApplied() {
		return dateApplied;
	}

	public void setDateApplied(LocalDate dateApplied) {
		this.dateApplied = dateApplied;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ApplicationStatus getStatus() {
		return status;
	}

	public void setStatus(ApplicationStatus status) {
		this.status = status;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Override
	public String toString() {
		return "JobApplication [user=" + user + ", jobTitle=" + jobTitle + ", companyName=" + companyName + ", dateApplied="
				+ dateApplied + ", status=" + status + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + ((jobTitle == null) ? 0 : jobTitle.hashCode());
		result = prime * result + ((companyName == null) ? 0 : companyName.hashCode());
		result = prime * result + ((dateApplied == null) ? 0 : dateApplied.hashCode());
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
		JobApplication other = (JobApplication) obj;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		if (jobTitle == null) {
			if (other.jobTitle != null)
				return false;
		} else if (!jobTitle.equals(other.jobTitle))
			return false;
		if (companyName == null) {
			if (other.companyName != null)
				return false;
		} else if (!companyName.equals(other.companyName))
			return false;
		if (dateApplied == null) {
			if (other.dateApplied != null)
				return false;
		} else if (!dateApplied.equals(other.dateApplied))
			return false;
		return true;
	}

} 


