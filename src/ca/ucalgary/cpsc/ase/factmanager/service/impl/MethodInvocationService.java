package ca.ucalgary.cpsc.ase.factmanager.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import ca.ucalgary.cpsc.ase.common.ServiceDirectory;
import ca.ucalgary.cpsc.ase.common.entity.Assertion;
import ca.ucalgary.cpsc.ase.common.entity.Method;
import ca.ucalgary.cpsc.ase.common.entity.MethodInvocation;
import ca.ucalgary.cpsc.ase.common.entity.Position;
import ca.ucalgary.cpsc.ase.common.entity.TestMethod;
import ca.ucalgary.cpsc.ase.common.service.MethodInvocationServiceRemote;
import ca.ucalgary.cpsc.ase.factmanager.service.AbstractService;
import ca.ucalgary.cpsc.ase.factmanager.service.MethodInvocationServiceLocal;

@Stateless(name="MethodInvocationService", mappedName=ServiceDirectory.METHOD_INVOCATION_SERVICE)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MethodInvocationService extends AbstractService<MethodInvocation> implements MethodInvocationServiceLocal, MethodInvocationServiceRemote {

	private static Logger logger = Logger.getLogger(MethodInvocation.class);
	
	public MethodInvocationService() {
		super(MethodInvocation.class);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)	
	public MethodInvocation create(TestMethod tm, Method m, Assertion a, Position p) {
		MethodInvocation mi = new MethodInvocation();
		mi.setTestmethod(tm);
		mi.setMethod(m);
		mi.setAssertion(a);
		mi.setPosition(p);
		create(mi);
		return mi;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)	
	public void addDataFlowRelationship(MethodInvocation from,
			MethodInvocation to) {
		if (from.getDataFlowsTo() == null) {
			from.setDataflowssTo(new HashSet<MethodInvocation>());
		}
		from.getDataFlowsTo().add(to);
		update(from);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)	
	public List getTestsWithMethodToMethodDataFlowPath(Set<Integer> from, Set<Integer> to) {
		return getEntityManager().createNamedQuery("MethodToMethodDataFlowPath")
			.setParameter("list1", from)
			.setParameter("list2", to)
			.getResultList();
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)	
	public List getTestsWithMethodToAssertionDataFlowPath(Set<Integer> from, Assertion to) {
		return getEntityManager().createNamedQuery("MethodToAssertionDataFlowPath")
			.setParameter("list", from)
			.setParameter("assertion", to)
			.getResultList();
	}

	@Override
	public List getMatchingMethodToMethodDataFlows(Integer id,
			Set<Integer> from, Set<Integer> to) {
		return getEntityManager().createNamedQuery("MatchingMethodToMethodDataFlows")
			.setParameter("id", id)
			.setParameter("list1", from)
			.setParameter("list2", to)
			.getResultList();		
	}

	@Override
	public List getMatchingMethodToAssertionDataFlows(Integer id,
			Set<Integer> from, Assertion assertion) {
		return getEntityManager().createNamedQuery("MatchingMethodToAssertionDataFlows")
			.setParameter("id", id)
			.setParameter("list", from)
			.setParameter("assertion", assertion)
			.getResultList();	
	}
	
}
