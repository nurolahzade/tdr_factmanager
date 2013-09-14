package ca.ucalgary.cpsc.ase.factmanager.service;

import java.util.List;

import javax.ejb.Local;

import ca.ucalgary.cpsc.ase.common.entity.RepositoryFile;

@Local
public interface RepositoryFileServiceLocal extends ServiceFacade<RepositoryFile> {

	public RepositoryFile create(String path);

	public void visit(RepositoryFile file);

	public void skip(RepositoryFile file);

	public List<RepositoryFile> findUnvisited(int maxResults);

}