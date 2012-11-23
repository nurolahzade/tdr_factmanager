package ca.ucalgary.cpsc.ase.QueryManager.heuristic;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ca.ucalgary.cpsc.ase.QueryManager.DatabaseHeuristic;
import ca.ucalgary.cpsc.ase.QueryManager.Query;
import ca.ucalgary.cpsc.ase.QueryManager.ResultItem;
import ca.ucalgary.cpsc.ase.QueryManager.query.QueryAssertion;
import ca.ucalgary.cpsc.ase.FactManager.entity.Assertion;
import ca.ucalgary.cpsc.ase.FactManager.entity.CodeEntity;
import ca.ucalgary.cpsc.ase.FactManager.service.AssertionService;
import ca.ucalgary.cpsc.ase.FactManager.service.ClazzService;

public class AssertionHeuristic extends DatabaseHeuristic {

	@Override
	protected Set resolve(Query q) {
		Set<Integer> resolvedAssertions = new HashSet<Integer>();
		if (q.getAssertions() != null) {
			AssertionService service = new AssertionService();
			for (QueryAssertion qAssertion : q.getAssertions()) {
				Assertion assertion = service.find(qAssertion.getType());
				if (assertion != null)
					resolvedAssertions.add(assertion.getId());
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
		ClazzService service = new ClazzService();
		return service.matchAssertions(resolved);			
	}

	@Override
	protected long getNormalizationFactor(Query q, ResultItem item) {
		return q.getAssertions().size();
	}

	@Override
	public List<Assertion> getMatchingItems(Integer id, Query q) {
		AssertionService service = new AssertionService();
		return service.getMatchingAssertions(id, resolve(q));
	}

}
	