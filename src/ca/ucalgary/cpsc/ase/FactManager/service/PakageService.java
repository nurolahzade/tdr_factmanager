package ca.ucalgary.cpsc.ase.FactManager.service;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import ca.ucalgary.cpsc.ase.common.entity.Pakage;

@Stateless(name="PakageService", mappedName="ejb/PakageService")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class PakageService extends AbstractService<Pakage> implements PackageServiceLocal {

	private static Logger logger = Logger.getLogger(PakageService.class);

	public PakageService() {
		super(Pakage.class);
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)	
	public Pakage create(String name) {
		Pakage pakage = new Pakage();
		pakage.setName(name);
		create(pakage);
		return pakage;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)	
	public Pakage createOrGet(String name) {
		Pakage pakage = find(name);
		if (pakage == null) {
			pakage = create(name);
		}
		return pakage;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)	
	public Pakage find(String name) {
		try {
			return (Pakage) getEntityManager().createNamedQuery("findPakageByFQN").setParameter("fqn", name).getSingleResult();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			return null;
		}		
	}

}
