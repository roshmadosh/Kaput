package link.hiroshisprojects.kaput.jobApplication;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.hateoas.RepresentationModel;

import link.hiroshisprojects.kaput.user.User;

@Entity
@Table(name="job_applications")
public class JobApplication extends RepresentationModel<JobApplication> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	@Column(name="job_title", nullable = false)
	@Size(max = 30)
	@NotEmpty(message = "Job title is required!")
	private String jobTitle;

	@Column(name = "company_name", nullable = false)
	@Size(min = 1, max = 55)
	@NotEmpty(message = "Company name required!")
	private String companyName;

	@Column(name="date_applied", nullable = false)
	@Past(message = "Apply date must be in the past.")
	@NotEmpty(message = "Apply date required!")
	private LocalDate dateApplied;

	@Column(name="status", nullable = false)
	@NotEmpty(message = "Status is required!")
	private ApplicationStatus status;

	public JobApplication(User user, String jobTitle, String companyName, LocalDate dateApplied, ApplicationStatus status) {
		this.user = user;
		this.jobTitle = jobTitle;
		this.dateApplied = dateApplied;
		this.status = status;
		this.companyName = companyName;
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
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (int) (id ^ (id >>> 32));
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
		if (id != other.id)
			return false;
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

	@Override
	public String toString() {
		return "JobApplication [user=" + user + ", jobTitle=" + jobTitle + ", companyName=" + companyName + ", dateApplied="
				+ dateApplied + ", status=" + status + "]";
	}


} 


