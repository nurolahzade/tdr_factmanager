package ca.ucalgary.cpsc.ase.factmanager.heuristic.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import ca.ucalgary.cpsc.ase.common.query.Query;
import ca.ucalgary.cpsc.ase.common.query.QueryAssertion;
import ca.ucalgary.cpsc.ase.common.entity.Assertion;
import ca.ucalgary.cpsc.ase.common.heuristic.ResultItem;
import ca.ucalgary.cpsc.ase.factmanager.heuristic.DatabaseHeuristic;
import ca.ucalgary.cpsc.ase.factmanager.service.AssertionServiceLocal;
import ca.ucalgary.cpsc.ase.factmanager.service.ClazzServiceLocal;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class AssertionHeuristic extends DatabaseHeuristic {

	@EJB private AssertionServiceLocal assertionService;
	@EJB private ClazzServiceLocal clazzService;
	
	@Override
	protected Set resolve(Query q) {
		Set<Integer> resolvedAssertions = new HashSet<Integer>();
		if (q.getAssertions() != null) {
			for (QueryAssertion qAssertion : q.getAssertions()) {
				Assertion assertion = assertionService.find(qAssertion.getType());
				if (assertion != null) {
					resolvedAssertions.add(assertion.getId());
					qAssertion.setResolved(true);
				}
				else {
					qAssertion.setResolved(false);
				}
			}
		}
		return resolvedAssertions;
	}

	@Override
	public String getName() {
		return "A";
	}
	
	@Override
	public String getFullName() {
		return "Assertions";
	}	

	@Override
	protected List retrieve(Set resolved) {
		return clazzService.matchAssertions(resolved);			
	}

	@Override
	protected long getNormalizationFactor(Query q, ResultItem item) {
		return q.getAssertions().size();
	}

	@Override
	public List<String> getMatchingItems(Integer id, Query q) {
		return entityToString(assertionService.getMatchingAssertions(id, resolve(q)));
	}

}
	