package ca.ucalgary.cpsc.ase.FactManager.service;

import javax.ejb.Remote;

import ca.ucalgary.cpsc.ase.common.entity.Project;

@Remote
public interface ProjectServiceRemote {

	public Project create(String name, String version);
	
}
