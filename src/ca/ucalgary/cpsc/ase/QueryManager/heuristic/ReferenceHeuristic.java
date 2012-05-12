package ca.ucalgary.cpsc.ase.QueryManager.heuristic;

import java.util.List;

import ca.ucalgary.cpsc.ase.FactManager.service.TestMethodService;
import ca.ucalgary.cpsc.ase.QueryManager.Heuristic;
import ca.ucalgary.cpsc.ase.QueryManager.Query;
import ca.ucalgary.cpsc.ase.QueryManager.ResultItem;

public class ReferenceHeuristic implements Heuristic {

	@Override
	public List<ResultItem> match(Query q) {
		TestMethodService service = new TestMethodService();
		// extract fqns from query
		service.matchReferences(null);
		// convert results
		return null;
	}
	
	

}
