package ca.ucalgary.cpsc.ase.FactManager.service;

import org.apache.log4j.Logger;

import ca.ucalgary.cpsc.ase.FactManager.entity.Pakage;

public class PakageService extends AbstractService<Pakage> {

	private static Logger logger = Logger.getLogger(PakageService.class);

	public PakageService() {
		super(Pakage.class);
	}
	
	public Pakage create(String name) {
		beginTransaction();
		Pakage pakage = new Pakage();
		pakage.setName(name);
		create(pakage);
		commitTransaction();
		return pakage;
	}
	
	public Pakage createOrGet(String name) {
		Pakage pakage = find(name);
		if (pakage == null) {
			pakage = create(name);
		}
		return pakage;
	}
	
	public Pakage find(String name) {
		try {
			return (Pakage) getEntityManager().createNamedQuery("findPakageByFQN").setParameter("fqn", name).getSingleResult();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			return null;
		}		
	}

}
