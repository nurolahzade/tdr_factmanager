package ca.ucalgary.cpsc.ase.factmanager.service.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import ca.ucalgary.cpsc.ase.common.ServiceDirectory;
import ca.ucalgary.cpsc.ase.common.entity.Project;
import ca.ucalgary.cpsc.ase.factmanager.service.AbstractService;
import ca.ucalgary.cpsc.ase.factmanager.service.ProjectServiceLocal;
import ca.ucalgary.cpsc.ase.common.service.ProjectServiceRemote;

@Stateless(name="ProjectService", mappedName=ServiceDirectory.PROJECT_SERVICE)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ProjectService extends AbstractService<Project> implements ProjectServiceRemote, ProjectServiceLocal {

	public ProjectService() {
		super(Project.class);
	}
		
	@Override	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Project create(String name) {
		Project project = new Project();
		project.setName(name);
		create(project);

		return project;
	}

}
