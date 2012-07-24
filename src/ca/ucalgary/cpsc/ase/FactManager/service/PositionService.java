package ca.ucalgary.cpsc.ase.FactManager.service;

import ca.ucalgary.cpsc.ase.FactManager.entity.Position;

public class PositionService extends AbstractService<Position> {

	public PositionService() {
		super(Position.class);
	}
	
	public Position create(Integer start, Integer length) {
		beginTransaction();
		Position position = new Position();
		position.setStart(start);
		position.setLength(length);
		create(position);
		commitTransaction();
		return position;
	}

}
