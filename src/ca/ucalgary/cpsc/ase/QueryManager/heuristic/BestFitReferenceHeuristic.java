package ca.ucalgary.cpsc.ase.QueryManager.heuristic;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import ca.ucalgary.cpsc.ase.FactManager.entity.TestMethod;
import ca.ucalgary.cpsc.ase.FactManager.service.TestMethodService;
import ca.ucalgary.cpsc.ase.QueryManager.Query;
import ca.ucalgary.cpsc.ase.QueryManager.ResultItem;

public class BestFitReferenceHeuristic extends InvocationHeuristic {

	@Override
	public SortedMap<Integer, ResultItem> match(Query q) {
		
		SortedMap<Integer, ResultItem> simpleResults = super.match(q);
		
		Set<Integer> testMethods = new HashSet<Integer>(); 		
		testMethods.addAll(simpleResults.keySet());
		
		TestMethodService service = new TestMethodService();
		List dbResults = service.matchBestFitInvocations(testMethods, resolvedInvocations);
		
		SortedMap<Integer, ResultItem> results = new TreeMap<Integer, ResultItem>();		
		
		for (ResultItem result : simpleResults.values()) {
			// if there is a match in dbResults with the same id, then test method has unmatched invocations too
			// otherwise, test method has no unmatched invocations
		}
		
		return null;
	}

	
	
}
