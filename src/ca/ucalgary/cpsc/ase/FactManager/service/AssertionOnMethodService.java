package ca.ucalgary.cpsc.ase.FactManager.service;

import org.apache.log4j.Logger;

import ca.ucalgary.cpsc.ase.FactManager.entity.Assertion;
import ca.ucalgary.cpsc.ase.FactManager.entity.AssertionOnMethod;
import ca.ucalgary.cpsc.ase.FactManager.entity.AssertionOnMethodPK;
import ca.ucalgary.cpsc.ase.FactManager.entity.AssertionType;
import ca.ucalgary.cpsc.ase.FactManager.entity.Method;
import ca.ucalgary.cpsc.ase.FactManager.entity.TestMethod;

public class AssertionOnMethodService extends AbstractService<AssertionOnMethod> {

	private static Logger logger = Logger.getLogger(AssertionOnMethodService.class);

	public AssertionOnMethodService() {
		super(AssertionOnMethod.class);
	}

	public AssertionOnMethod find(Assertion assertion, Method method, TestMethod testMethod) {
		try {
			return (AssertionOnMethod) getEntityManager().createNamedQuery("Find").
				setParameter("assertion", assertion).
				setParameter("method", method).
				setParameter("testMethod", testMethod).
				getSingleResult();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			return null;
		}
	}
	
	public AssertionOnMethod createOrGet(Assertion assertion, Method method, TestMethod testMethod) {
		AssertionOnMethod aom = find(assertion, method, testMethod); 
		if (aom == null) {
			aom = create(assertion, method, testMethod);
		}
		return aom;
	}

	public AssertionOnMethod create(Assertion assertion, Method method, TestMethod testMethod) {
		beginTransaction();
		AssertionOnMethod aom = new AssertionOnMethod();
		AssertionOnMethodPK pk = new AssertionOnMethodPK();
		pk.setAssertionId(assertion.getId());
		pk.setMethodId(method.getId());
		pk.setTestMethodId(testMethod.getId());
		aom.setId(pk);
		aom.setAssertion(assertion);
		aom.setMethod(method);
		aom.setTestMethod(testMethod);
		create(aom);
		commitTransaction();
		return aom;
	}
	
}
