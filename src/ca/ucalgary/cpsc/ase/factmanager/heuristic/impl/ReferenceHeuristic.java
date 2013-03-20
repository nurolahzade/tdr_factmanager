package ca.ucalgary.cpsc.ase.factmanager.heuristic.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import ca.ucalgary.cpsc.ase.common.entity.Clazz;
import ca.ucalgary.cpsc.ase.common.entity.Reference;
import ca.ucalgary.cpsc.ase.common.heuristic.ResultItem;
import ca.ucalgary.cpsc.ase.common.query.Query;
import ca.ucalgary.cpsc.ase.common.query.QueryReference;
import ca.ucalgary.cpsc.ase.factmanager.heuristic.DatabaseHeuristic;
import ca.ucalgary.cpsc.ase.factmanager.service.ClazzServiceLocal;
import ca.ucalgary.cpsc.ase.factmanager.service.ReferenceServiceLocal;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ReferenceHeuristic extends DatabaseHeuristic {
	
	@EJB private ClazzServiceLocal clazzService;
	@EJB private ReferenceServiceLocal referenceService;
	
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
		Set<String> resolvedFQNs = new HashSet<String>();		
		List<QueryReference> qReferences = q.getReferences();
		
		for (QueryReference qReference : qReferences) {
			Clazz clazz = clazzService.find(qReference.getClazzFqn());
			if (clazz != null) {
				resolvedFQNs.add(qReference.getClazzFqn());
				qReference.setResolved(true);
			}
			else {
				qReference.setResolved(false);
			}
		}
		return resolvedFQNs;
	}

	@Override
	protected List retrieve(Set resolved) {
		return clazzService.matchReferences(resolved);
	}

	@Override
	protected long getNormalizationFactor(Query q, ResultItem item) {
		return q.getReferences().size();
	}

	@Override
	public List<String> getMatchingItems(Integer id, Query q) {
		return entityToString(referenceService.getMatchingReferences(id, resolve(q)));
	}	

}
