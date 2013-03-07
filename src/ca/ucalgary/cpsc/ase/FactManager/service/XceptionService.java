package ca.ucalgary.cpsc.ase.FactManager.service;

import java.util.HashSet;
import java.util.Set;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import ca.ucalgary.cpsc.ase.common.entity.Clazz;
import ca.ucalgary.cpsc.ase.common.entity.Xception;
import ca.ucalgary.cpsc.ase.common.entity.TestMethod;

@Stateless(name="XceptionService", mappedName="ejb/XceptionService")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class XceptionService extends AbstractService<Xception> implements XceptionServiceLocal {

	private static Logger logger = Logger.getLogger(XceptionService.class);

	public XceptionService() {
		super(Xception.class);
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)	
	public Xception create(Clazz clazz, TestMethod method) {
		Xception xception = new Xception();
		xception.setClazz(clazz);
		Set<TestMethod> testMethods = new HashSet<TestMethod>();
		testMethods.add(method);
		xception.setTestMethods(testMethods);
		create(xception);

		return xception;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)	
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
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)	
	public Xception find(Clazz clazz) {
		try {
			return (Xception) getEntityManager().createNamedQuery("FindByClazz").
				setParameter("clazz", clazz).getSingleResult();
			} catch (Exception e) {
			logger.debug(e.getMessage());
			return null;
		}		
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)	
	protected void addTestMethod(Xception exception, TestMethod testMethod) {
		Set<TestMethod> testMethods = exception.getTestMethods();
		if (testMethods != null) {
			if (testMethods.contains(testMethod))
				return;			
		}
		else {
			testMethods = new HashSet<TestMethod>();
			exception.setTestMethods(testMethods);
		}

		testMethods.add(testMethod);
		update(exception);
	}
	
}
