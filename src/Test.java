import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import ca.ucalgary.cpsc.ase.FactManager.entity.Project;
import ca.ucalgary.cpsc.ase.FactManager.service.ProjectService;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ProjectService projectService = new ProjectService();
		List<Project> projects = projectService.findAll();
		for (Project project : projects)
			System.out.println(project);
	}

}
