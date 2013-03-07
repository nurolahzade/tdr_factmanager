package ca.ucalgary.cpsc.ase.FactManager.service;

import java.util.List;

import javax.ejb.Remote;

import ca.ucalgary.cpsc.ase.common.entity.RepositoryFile;

@Remote
public interface RepositoryFileServiceRemote {

	public RepositoryFile create(String path);

	public void visit(RepositoryFile file);

	public void skip(RepositoryFile file);

	public List<RepositoryFile> findUnvisited();

}