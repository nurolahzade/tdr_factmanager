package ca.ucalgary.cpsc.ase.factmanager.service.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import ca.ucalgary.cpsc.ase.common.ServiceDirectory;
import ca.ucalgary.cpsc.ase.common.entity.Position;
import ca.ucalgary.cpsc.ase.common.service.PositionServiceRemote;
import ca.ucalgary.cpsc.ase.factmanager.service.AbstractService;
import ca.ucalgary.cpsc.ase.factmanager.service.PositionServiceLocal;

@Stateless(name="PositionService", mappedName=ServiceDirectory.POSITION_SERVICE)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class PositionService extends AbstractService<Position> implements PositionServiceLocal, PositionServiceRemote {

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
