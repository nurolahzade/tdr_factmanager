package ca.ucalgary.cpsc.ase.FactManager.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import ca.ucalgary.cpsc.ase.FactManager.entity.Clazz;
import ca.ucalgary.cpsc.ase.FactManager.entity.TestMethod;

public class TestMethodService extends AbstractService<TestMethod> {

	public TestMethodService() {
		super(TestMethod.class);
	}
	
	public TestMethod create(String name, Clazz testClass) {
		beginTransaction();
		TestMethod testMethod = new TestMethod();
		testMethod.setName(name);
		testMethod.setLastModified(new Date());
		testMethod.setClazz(testClass);
		create(testMethod);
		commitTransaction();
		return testMethod;
	}
	
	public List matchReferences(Set<String> fqns) {
		return getEntityManager().createNamedQuery("MatchReference").
			setParameter("fqns", fqns).
			getResultList();
	}
	
	public List matchInvocations(Set<Integer> methods) {		
		return getEntityManager().createNamedQuery("MatchSimpleCall").
				setParameter("list", methods).
				getResultList();
	}
	
	public List matchBestFitInvocations(Set<Integer> testMethods, Set<Integer> methods) {
		return getEntityManager().createNamedQuery("MatchBestFitCall").
				setParameter("list1", testMethods).
				setParameter("list2", methods).
				getResultList();
	}

	
}
