package ca.ucalgary.cpsc.ase.QueryManager.heuristic;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ca.ucalgary.cpsc.ase.FactManager.entity.Reference;
import ca.ucalgary.cpsc.ase.FactManager.service.ClazzService;
import ca.ucalgary.cpsc.ase.FactManager.service.ReferenceService;
import ca.ucalgary.cpsc.ase.QueryManager.DatabaseHeuristic;
import ca.ucalgary.cpsc.ase.QueryManager.Query;
import ca.ucalgary.cpsc.ase.QueryManager.ResultItem;
import ca.ucalgary.cpsc.ase.QueryManager.query.QueryReference;

public class ReferenceHeuristic extends DatabaseHeuristic {
	
	@Override
	public String getName() {
		return "R";
	}

	@Override
	public String getFullName() {
		return "References";
	}

	@Override
	protected Set resolve(Query q) {
		Set<String> resolvedFqns = new HashSet<String>();
		
		// all references in user test
		List<QueryReference> qReferences = q.getReferences();
		
		// extract fqns from query
		for (QueryReference qReference : qReferences) {
			if (! UNKNOWN.equals(qReference.getClazzFqn())) {
				resolvedFqns.add(qReference.getClazzFqn());				
			}
		}
		return resolvedFqns;
	}

	@Override
	protected List retrieve(Set resolved) {
		ClazzService service = new ClazzService();
		return service.matchReferences(resolved);
	}

	@Override
	protected long getNormalizationFactor(Query q, ResultItem item) {
		return q.getReferences().size();
	}

	@Override
	public List<Reference> getMatchingItems(Integer id, Query q) {
		ReferenceService service = new ReferenceService();
		return service.getMatchingReferences(id, resolve(q));
	}	

}
