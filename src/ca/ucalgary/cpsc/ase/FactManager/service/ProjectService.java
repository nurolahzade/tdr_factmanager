package ca.ucalgary.cpsc.ase.FactManager.service;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import ca.ucalgary.cpsc.ase.common.entity.Project;

@Stateless(name="ProjectService", mappedName="ejb/ProjectService")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ProjectService extends AbstractService<Project> implements ProjectServiceRemote, ProjectServiceLocal {

	public ProjectService() {
		super(Project.class);
	}
		
	@Override	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Project create(String name, String version) {
		Project project = new Project();
		project.setName(name);
		project.setVersion(version);
		create(project);

		return project;
	}

}
