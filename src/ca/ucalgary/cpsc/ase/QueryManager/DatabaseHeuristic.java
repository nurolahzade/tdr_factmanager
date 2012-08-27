package ca.ucalgary.cpsc.ase.QueryManager;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ca.ucalgary.cpsc.ase.FactManager.entity.TestMethod;

public abstract class DatabaseHeuristic implements Heuristic {
	
	protected Map<Integer, ResultItem> parse(List rawResults) {
		Map<Integer, ResultItem> results = new LinkedHashMap<Integer, ResultItem>();
		
		System.out.print(this.getClass().getName() + ": ");
		for (int i = 0; i < rawResults.size(); ++i) {
			ResultItem result = new ResultItem();
			Object[] databaseResult = (Object[]) rawResults.get(i);
			TestMethod tm = (TestMethod) databaseResult[0];
			result.setTarget(tm);
			result.setScore(((Long) databaseResult[1]).doubleValue());
			results.put(tm.getId(), result);
			System.out.print(tm.getId() + ", ");
		}
		System.out.println();
		return results;
	}
	
}
