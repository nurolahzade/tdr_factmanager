package ca.ucalgary.cpsc.ase.FactManager.service;

import javax.ejb.Local;

import ca.ucalgary.cpsc.ase.common.entity.Project;

@Local
public interface ProjectServiceLocal extends LocalEJB<Project> {

	public Project create(String name, String version);

}