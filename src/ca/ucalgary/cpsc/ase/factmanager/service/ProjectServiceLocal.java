package ca.ucalgary.cpsc.ase.factmanager.service;

import javax.ejb.Local;

import ca.ucalgary.cpsc.ase.common.entity.Project;

@Local
public interface ProjectServiceLocal extends ServiceFacade<Project> {

	public Project create(String name, String version);

}