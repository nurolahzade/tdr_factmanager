package ca.ucalgary.cpsc.ase.FactManager.service;

import javax.ejb.Local;

import ca.ucalgary.cpsc.ase.common.entity.Pakage;

@Local
public interface PakageServiceLocal extends LocalEJB<Pakage> {

	public Pakage create(String name);

	public Pakage createOrGet(String name);

	public Pakage find(String name);

}