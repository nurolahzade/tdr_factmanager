package ca.ucalgary.cpsc.ase.QueryManager.heuristic;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ca.ucalgary.cpsc.ase.QueryManager.Heuristic;
import ca.ucalgary.cpsc.ase.QueryManager.Query;
import ca.ucalgary.cpsc.ase.QueryManager.ResultItem;
import ca.ucalgary.cpsc.ase.QueryManager.query.QueryAssertion;
import ca.ucalgary.cpsc.ase.FactManager.entity.Assertion;
import ca.ucalgary.cpsc.ase.FactManager.entity.TestMethod;
import ca.ucalgary.cpsc.ase.FactManager.service.AssertionService;
import ca.ucalgary.cpsc.ase.FactManager.service.TestMethodService;

public class AssertionHeuristic implements Heuristic {

	protected Set<Integer> resolvedAssertions = new HashSet<Integer>();
	
	@Override
	public Map<Integer, ResultItem> match(Query q) {
		resolveAssertions(q);
		
		TestMethodService service = new TestMethodService();
		List dbResults = service.matchAssertions(resolvedAssertions);
		
		Map<Integer, ResultItem> results = parse(dbResults);
		
		// normalize?
		
		return results;
	}

	private Map<Integer, ResultItem> parse(List rawResults) {
		Map<Integer, ResultItem> results = new LinkedHashMap<Integer, ResultItem>();
		
		for (int i = 0; i < rawResults.size(); ++i) {
			ResultItem result = new ResultItem();
			Object[] databaseResult = (Object[]) rawResults.get(i);
			TestMethod tm = (TestMethod) databaseResult[0];
			result.setTarget(tm);
			result.setScore(((Long) databaseResult[1]).doubleValue());
			results.put(tm.getId(), result);
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
