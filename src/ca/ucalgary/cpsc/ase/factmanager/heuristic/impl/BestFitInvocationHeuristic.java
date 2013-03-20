package ca.ucalgary.cpsc.ase.factmanager.heuristic.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import ca.ucalgary.cpsc.ase.common.heuristic.ResultItem;
import ca.ucalgary.cpsc.ase.common.query.Query;
import ca.ucalgary.cpsc.ase.factmanager.service.ClazzServiceLocal;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class BestFitInvocationHeuristic extends InvocationHeuristic {

	@EJB private ClazzServiceLocal clazzService;
	
	@Override
	public String getName() {
		return "BFI";
	}

	@Override
	public String getFullName() {
		return "Best Fit Invocations";
	}	
	
	@Override
	protected long getNormalizationFactor(Query q, ResultItem item) {
		return clazzService.getInvocationsCount(item.getTarget());
	}

}
