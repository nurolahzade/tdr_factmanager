package ca.ucalgary.cpsc.ase.FactManager.service;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import ca.ucalgary.cpsc.ase.common.entity.Position;

@Stateless(name="PositionService", mappedName="ejb/PositionService")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class PositionService extends AbstractService<Position> implements PositionServiceLocal {

	public PositionService() {
		super(Position.class);
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)	
	public Position create(Integer start, Integer length) {
		Position position = new Position();
		position.setStart(start);
		position.setLength(length);
		create(position);
		return position;
	}

}
