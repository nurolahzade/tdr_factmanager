package ca.ucalgary.cpsc.ase.FactManager.service;

import ca.ucalgary.cpsc.ase.FactManager.entity.Project;

public class ProjectService extends AbstractService<Project> {

	public ProjectService() {
		super(Project.class);
	}
	
	public Project create(String name, String version) {
		beginTransaction();
		Project project = new Project();
		project.setName(name);
		project.setVersion(version);
		create(project);
		commitTransaction();
		return project;
	}

}
