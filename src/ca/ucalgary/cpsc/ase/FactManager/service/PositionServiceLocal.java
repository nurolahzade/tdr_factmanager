package ca.ucalgary.cpsc.ase.FactManager.service;

import javax.ejb.Local;

import ca.ucalgary.cpsc.ase.common.entity.Position;

@Local
public interface PositionServiceLocal extends LocalEJB<Position> {

	public Position create(Integer start, Integer length);

}