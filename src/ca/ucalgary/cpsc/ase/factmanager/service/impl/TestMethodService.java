package ca.ucalgary.cpsc.ase.factmanager.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import ca.ucalgary.cpsc.ase.common.ServiceDirectory;
import ca.ucalgary.cpsc.ase.common.entity.Clazz;
import ca.ucalgary.cpsc.ase.common.entity.Position;
import ca.ucalgary.cpsc.ase.common.entity.TestMethod;
import ca.ucalgary.cpsc.ase.common.service.TestMethodServiceRemote;
import ca.ucalgary.cpsc.ase.factmanager.service.AbstractService;
import ca.ucalgary.cpsc.ase.factmanager.service.TestMethodServiceLocal;

@Stateless(name="TestMethodService", mappedName=ServiceDirectory.TEST_METHOD_SERVICE)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class TestMethodService extends AbstractService<TestMethod> implements TestMethodServiceLocal, TestMethodServiceRemote {

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
	@TransactionAttribute(TransactionAttributeType.REQUIRED)	
	public List matchReferences(Set<String> fqns) {
        return getEntityManager().createNamedQuery("MatchReference").
            setParameter("fqns", fqns).
            getResultList();
    }
     
    @Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)	
	public List matchInvocations(Set<Integer> methods) {     
        return getEntityManager().createNamedQuery("MatchSimpleCall").
                setParameter("list", methods).
                getResultList();
    }
     
    @Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)	
	public List matchAssertions(Set<Integer> assertions) {
        return getEntityManager().createNamedQuery("MatchAssertion").
                setParameter("list", assertions).
                getResultList();
    }
     
    @Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)	
	public List matchAssertionParameters(Set<Integer> methods) {
        return getEntityManager().createNamedQuery("MatchAssertionParameter").
                setParameter("list", methods).
                getResultList();
    }
     
    @Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)	
	public Long getInvocationsCount(TestMethod tm) {
        return (Long) getEntityManager().createNamedQuery("TotalMethodsInTestMethods").
            setParameter("testMethod", tm).
            getSingleResult();
    }
    
}
