package ca.ucalgary.cpsc.ase.factmanager.heuristic.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import ca.ucalgary.cpsc.ase.common.query.Query;
import ca.ucalgary.cpsc.ase.factmanager.heuristic.SolrClassSignatureQueryProcessor;
import ca.ucalgary.cpsc.ase.factmanager.heuristic.SolrHeuristicHelper;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class InterfaceKeywordHeuristic extends KeywordHeuristic {

	private String target;
	
	@Override
	public String getName() {
		return "IK";
	}

	@Override
	public String getFullName() {
		return "Interface Keywords";
	}
	
	public void setTarget(String target) {
		this.target = target;
	}
	
	@Override
	protected SolrHeuristicHelper getSolrHeuristicHelper(Query q)
			throws Exception {
		return new SolrClassSignatureQueryProcessor(q, target);
	}
}
