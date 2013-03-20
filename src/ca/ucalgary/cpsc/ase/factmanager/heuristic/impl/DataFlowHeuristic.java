package ca.ucalgary.cpsc.ase.factmanager.heuristic.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import ca.ucalgary.cpsc.ase.common.entity.Assertion;
import ca.ucalgary.cpsc.ase.common.entity.Clazz;
import ca.ucalgary.cpsc.ase.common.heuristic.ResultItem;
import ca.ucalgary.cpsc.ase.common.query.Query;
import ca.ucalgary.cpsc.ase.common.query.QueryAssertion;
import ca.ucalgary.cpsc.ase.common.query.QueryInvocation;
import ca.ucalgary.cpsc.ase.common.query.QueryMethod;
import ca.ucalgary.cpsc.ase.factmanager.heuristic.DatabaseHeuristic;
import ca.ucalgary.cpsc.ase.factmanager.heuristic.SolrMethodSignatureQueryProcessor;
import ca.ucalgary.cpsc.ase.factmanager.service.AssertionServiceLocal;
import ca.ucalgary.cpsc.ase.factmanager.service.MethodInvocationServiceLocal;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class DataFlowHeuristic extends DatabaseHeuristic {

	@EJB private MethodInvocationServiceLocal invocationService;
	@EJB private AssertionServiceLocal assertionService;
	
	private Map<Integer, ResultItem> aggregated;
	
	@Override
	public String getName() {
		return "DF";
	}

	@Override
	public String getFullName() {
		return "Data Flow";
	}

	@Override
	public Map<Integer, ResultItem> match(Query q) throws Exception {
		aggregated = new HashMap<Integer, ResultItem>();
		for (QueryMethod method : q.getDataFlows().keySet()) {
			SolrMethodSignatureQueryProcessor fromProcessor = new SolrMethodSignatureQueryProcessor(method);
			Map<Integer, ResultItem> matchedFromMethods = fromProcessor.fetch();
			for (QueryInvocation invocation : q.getDataFlows().get(method)) {
				Map<Integer, ResultItem> testClazzes = null;
				if (invocation instanceof QueryMethod) {					
					SolrMethodSignatureQueryProcessor toProcessor = new SolrMethodSignatureQueryProcessor((QueryMethod) invocation);
					Map<Integer, ResultItem> matchedToMethods = toProcessor.fetch();
					if (matchedFromMethods.size() > 0 && matchedToMethods.size() > 0) {
						testClazzes = parse(invocationService.getTestsWithMethodToMethodDataFlowPath(matchedFromMethods.keySet(), matchedToMethods.keySet()));						
					}
				}
				else {
					Assertion assertion = assertionService.find(((QueryAssertion) invocation).getType());
					if (matchedFromMethods.size() > 0) {
						testClazzes = parse(invocationService.getTestsWithMethodToAssertionDataFlowPath(matchedFromMethods.keySet(), assertion));						
					}
				}
				if (testClazzes != null) {
					aggregate(testClazzes);					
				}
			}
		}
		
		Map<Integer, ResultItem> results = sort(aggregated);
		normalize(q, results);
		
		return results;
	}	

	@Override
	protected Map<Integer, ResultItem> parse(List rawResults) {
		Map<Integer, ResultItem> results = new LinkedHashMap<Integer, ResultItem>();
		
		for (Clazz c : (List<Clazz>) rawResults) {
			ResultItem result = new ResultItem();
			result.setTarget(c);
			//TODO score has to match the max(s1 + s2) 
			// s1 and s2 are the similarity scores of the two ends of the data flow edge
			result.setScore(new Double(1));
			results.put(c.getId(), result);
		}
		return results;
	}

	private void aggregate(Map<Integer, ResultItem> testClazzes) {
		for (Integer id : testClazzes.keySet()) {
			if (aggregated.containsKey(id)) {
				aggregated.get(id).setScore(aggregated.get(id).getScore() + testClazzes.get(id).getScore());
			}
			else {
				aggregated.put(id, testClazzes.get(id));
			}
		}
	}

	@Override
	public List getMatchingItems(Integer id, Query q) {
		return new ArrayList();
	}

	@Override
	protected Set resolve(Query q) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected List retrieve(Set resolved) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected long getNormalizationFactor(Query q, ResultItem item) {
		int count = 0;
		for (QueryMethod method : q.getDataFlows().keySet())
			count += q.getDataFlows().get(method).size();
		return count;
	}


}
