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

	protected Set<Integer> resolvedAssertions = new HashSet<Integer>();
	
	@Override
	public Map<Integer, ResultItem> match(Query q) {
		Map<Integer, ResultItem> results;
		
		resolveAssertions(q);
		
		if (resolvedAssertions.size() > 0) {		
			TestMethodService service = new TestMethodService();
			List dbResults = service.matchAssertions(resolvedAssertions);			
			results = parse(dbResults);
		}
		else {
			results = new LinkedHashMap<Integer, ResultItem>();
		}
		
		return results;
	}

	private void resolveAssertions(Query q) {
		AssertionService service = new AssertionService();
		for (QueryAssertion qAssertion : q.getAssertions()) {
			Assertion assertion = service.find(qAssertion.getType());
			if (assertion != null)
				resolvedAssertions.add(assertion.getId());
		}		
	}

}
