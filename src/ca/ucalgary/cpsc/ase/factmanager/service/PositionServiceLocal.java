package ca.ucalgary.cpsc.ase.factmanager.service;

import javax.ejb.Local;

import ca.ucalgary.cpsc.ase.common.entity.Position;

@Local
public interface PositionServiceLocal extends ServiceFacade<Position> {

	public Position create(Integer start, Integer length);

}