package ca.ucalgary.cpsc.ase.QueryManager.heuristic;

import java.util.Map;

import ca.ucalgary.cpsc.ase.FactManager.service.TestMethodService;
import ca.ucalgary.cpsc.ase.QueryManager.Query;
import ca.ucalgary.cpsc.ase.QueryManager.ResultItem;

public class BestFitInvocationHeuristic extends InvocationHeuristic {

	@Override
	public Map<Integer, ResultItem> match(Query q) {
		
		Map<Integer, ResultItem> results = super.match(q);
		
		normalize(results);
		
		return sort(results);
	}

	private void normalize(Map<Integer, ResultItem> results) {
		TestMethodService service = new TestMethodService();
		for (ResultItem result : results.values()) {
			result.setScore(result.getScore() / service.getInvocationsCount(result.getTarget()));
		}		
	}

	@Override
	public String getName() {
		return "BFI";
	}
	
}
