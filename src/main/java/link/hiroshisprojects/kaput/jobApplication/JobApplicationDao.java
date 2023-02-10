package link.hiroshisprojects.kaput.jobApplication;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobApplicationDao extends CrudRepository<JobApplication, Long> {

	public List<JobApplication> findByUserId(long userId);

}
