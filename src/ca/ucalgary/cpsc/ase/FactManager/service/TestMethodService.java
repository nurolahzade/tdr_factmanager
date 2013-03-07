package ca.ucalgary.cpsc.ase.FactManager.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import ca.ucalgary.cpsc.ase.common.entity.Clazz;
import ca.ucalgary.cpsc.ase.common.entity.Position;
import ca.ucalgary.cpsc.ase.common.entity.TestMethod;

public class TestMethodService extends AbstractService<TestMethod> implements TestMethodServiceLocal {

	public TestMethodService() {
		super(TestMethod.class);
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)	
	public TestMethod create(String name, Clazz testClass, Position position) {
		TestMethod testMethod = new TestMethod();
		testMethod.setName(name);
		testMethod.setLastModified(new Date());
		testMethod.setClazz(testClass);
		testMethod.setPosition(position);
		create(testMethod);
		return testMethod;
	}

    @Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)	
	public List matchReferences(Set<String> fqns) {
        return getEntityManager().createNamedQuery("MatchReference").
            setParameter("fqns", fqns).
            getResultList();
    }
     
    @Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)	
	public List matchInvocations(Set<Integer> methods) {     
        return getEntityManager().createNamedQuery("MatchSimpleCall").
                setParameter("list", methods).
                getResultList();
    }
     
    @Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)	
	public List matchAssertions(Set<Integer> assertions) {
        return getEntityManager().createNamedQuery("MatchAssertion").
                setParameter("list", assertions).
                getResultList();
    }
     
    @Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)	
	public List matchAssertionParameters(Set<Integer> methods) {
        return getEntityManager().createNamedQuery("MatchAssertionParameter").
                setParameter("list", methods).
                getResultList();
    }
     
    @Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)	
	public Long getInvocationsCount(TestMethod tm) {
        return (Long) getEntityManager().createNamedQuery("TotalMethodsInTestMethods").
            setParameter("testMethod", tm).
            getSingleResult();
    }
    
}
