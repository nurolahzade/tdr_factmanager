package ca.ucalgary.cpsc.ase.factmanager.service;

import javax.ejb.Local;

import ca.ucalgary.cpsc.ase.common.entity.Pakage;

@Local
public interface PakageServiceLocal extends ServiceFacade<Pakage> {

	public Pakage create(String name);

	public Pakage createOrGet(String name);

	public Pakage find(String name);

}