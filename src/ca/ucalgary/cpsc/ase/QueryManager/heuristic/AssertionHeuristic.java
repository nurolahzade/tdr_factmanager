package ca.ucalgary.cpsc.ase.QueryManager.heuristic;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ca.ucalgary.cpsc.ase.QueryManager.DatabaseHeuristic;
import ca.ucalgary.cpsc.ase.QueryManager.Query;
import ca.ucalgary.cpsc.ase.QueryManager.ResultItem;
import ca.ucalgary.cpsc.ase.QueryManager.query.QueryAssertion;
import ca.ucalgary.cpsc.ase.FactManager.entity.Assertion;
import ca.ucalgary.cpsc.ase.FactManager.entity.TestMethod;
import ca.ucalgary.cpsc.ase.FactManager.service.AssertionService;
import ca.ucalgary.cpsc.ase.FactManager.service.TestMethodService;

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
	protected List retrieve(Set resolved) {
		TestMethodService service = new TestMethodService();
		return service.matchAssertions(resolved);			
	}

	@Override
	protected void normalize(Query q, Map<Integer, ResultItem> results) {
		for (ResultItem result : results.values()) {
			result.setScore(result.getScore() / q.getAssertions().size());
		}		
	}

}
