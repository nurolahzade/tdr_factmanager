package ca.ucalgary.cpsc.ase.QueryManager.heuristic;

import java.util.Map;

import ca.ucalgary.cpsc.ase.QueryManager.Query;
import ca.ucalgary.cpsc.ase.QueryManager.ResultItem;

public class BestFitInvocationHeuristic extends InvocationHeuristic {

	@Override
	public Map<Integer, ResultItem> match(Query q) {
		
		Map<Integer, ResultItem> results = super.match(q);
		
		normalize(results);

		// todo - return results sorted by score
		
		return results;
	}

	private void normalize(Map<Integer, ResultItem> results) {
		for (ResultItem result : results.values()) {
			result.setScore(result.getScore() / result.getTarget().getInvocations().size());
		}		
	}
	
	
}
