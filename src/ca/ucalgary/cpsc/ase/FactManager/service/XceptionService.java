package ca.ucalgary.cpsc.ase.FactManager.service;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import ca.ucalgary.cpsc.ase.FactManager.entity.Clazz;
import ca.ucalgary.cpsc.ase.FactManager.entity.Xception;
import ca.ucalgary.cpsc.ase.FactManager.entity.TestMethod;

public class XceptionService extends AbstractService<Xception> {

	private static Logger logger = Logger.getLogger(XceptionService.class);

	public XceptionService() {
		super(Xception.class);
	}
	
	public Xception create(Clazz clazz, TestMethod method) {
		beginTransaction();
		Xception xception = new Xception();
		xception.setClazz(clazz);
		Set<TestMethod> testMethods = new HashSet<TestMethod>();
		testMethods.add(method);
		xception.setTestMethods(testMethods);
		create(xception);
		commitTransaction();
		return xception;
	}
	
	public Xception createOrGet(Clazz clazz, TestMethod testMethod) {
		Xception xception = find(clazz);
		if (xception == null) {
			xception = create(clazz, testMethod);
		}
		else {
			addTestMethod(xception, testMethod);
		}
		return xception;
	}
	
	protected Xception find(Clazz clazz) {
		try {
			return (Xception) getEntityManager().createNamedQuery("FindByClazz").
				setParameter("clazz", clazz).getSingleResult();
			} catch (Exception e) {
			logger.debug(e.getMessage());
			return null;
		}		
	}
	
	protected void addTestMethod(Xception exceptioN, TestMethod testMethod) {
		Set<TestMethod> testMethods = exceptioN.getTestMethods();
		if (testMethods != null) {
			if (testMethods.contains(testMethod))
				return;			
		}
		else {
			testMethods = new HashSet<TestMethod>();
			exceptioN.setTestMethods(testMethods);
		}
		beginTransaction();
		testMethods.add(testMethod);
		update(exceptioN);
		commitTransaction();
	}
	
}
