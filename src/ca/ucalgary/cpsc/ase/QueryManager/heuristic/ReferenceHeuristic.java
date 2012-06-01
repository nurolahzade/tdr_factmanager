package ca.ucalgary.cpsc.ase.QueryManager.heuristic;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.LinkedHashMap;

import ca.ucalgary.cpsc.ase.FactManager.entity.TestMethod;
import ca.ucalgary.cpsc.ase.FactManager.service.TestMethodService;
import ca.ucalgary.cpsc.ase.QueryManager.Heuristic;
import ca.ucalgary.cpsc.ase.QueryManager.Query;
import ca.ucalgary.cpsc.ase.QueryManager.ResultItem;
import ca.ucalgary.cpsc.ase.QueryManager.query.QueryReference;

public class ReferenceHeuristic extends Heuristic {

	protected Set<String> resolvedFqns = new HashSet<String>();
	
	@Override
	public Map<Integer, ResultItem> match(Query q) {
		
		Map<Integer, ResultItem> results; 
		
		resolveReferenceTypes(q);
		
		if (resolvedFqns.size() > 0) {
			// find matching test methods
			TestMethodService service = new TestMethodService();
			List dbResults = service.matchReferences(resolvedFqns);

			// convert results
			results = parse(dbResults);			
		}
		else {
			results = new LinkedHashMap<Integer, ResultItem>();
		}
		
		return results;
	}

	protected void resolveReferenceTypes(Query q) {
		// all references in user test
		List<QueryReference> qReferences = q.getReferences();
		
		// extract fqns from query
		for (QueryReference qReference : qReferences) {
			resolvedFqns.add(qReference.getClazzFqn());
		}
	}	
	

}
