package ca.ucalgary.cpsc.ase.factmanager.heuristic.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import ca.ucalgary.cpsc.ase.common.entity.Clazz;
import ca.ucalgary.cpsc.ase.common.heuristic.ResultItem;
import ca.ucalgary.cpsc.ase.common.query.Query;
import ca.ucalgary.cpsc.ase.factmanager.heuristic.AbstractHeuristic;
import ca.ucalgary.cpsc.ase.factmanager.heuristic.SolrHeuristicHelper;
import ca.ucalgary.cpsc.ase.factmanager.heuristic.SolrNamesAndFQNsQueryProcessor;
import ca.ucalgary.cpsc.ase.factmanager.service.ClazzServiceLocal;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class KeywordHeuristic extends AbstractHeuristic {
	
	@EJB ClazzServiceLocal clazzService;
	
	@Override
	public String getName() {
		return "K";
	}

	@Override
	public String getFullName() {
		return "Keywords";
	}

	@Override
	public List<String> getMatchingItems(Integer id, Query q) {
		return new ArrayList<String>();
	}
	
	protected SolrHeuristicHelper getSolrHeuristicHelper(Query q) throws Exception {
		return new SolrNamesAndFQNsQueryProcessor(q);
	}

	@Override
	public Map<Integer, ResultItem> match(Query q) throws Exception {
		SolrHeuristicHelper helper = getSolrHeuristicHelper(q);
		Map<Integer, ResultItem> results = helper.fetch();
		for (Integer id : results.keySet()) {
			Clazz clazz = clazzService.find(id);
			results.get(id).setTarget(clazz);
		}
		return results;
	}
	
}
