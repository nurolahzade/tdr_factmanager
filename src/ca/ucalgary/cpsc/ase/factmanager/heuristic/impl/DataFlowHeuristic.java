package ca.ucalgary.cpsc.ase.factmanager.heuristic.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
import ca.ucalgary.cpsc.ase.common.entity.Invocation;
import ca.ucalgary.cpsc.ase.common.entity.Method;
import ca.ucalgary.cpsc.ase.common.heuristic.ResultItem;
import ca.ucalgary.cpsc.ase.common.query.Query;
import ca.ucalgary.cpsc.ase.common.query.QueryAssertion;
import ca.ucalgary.cpsc.ase.common.query.QueryInvocation;
import ca.ucalgary.cpsc.ase.common.query.QueryMethod;
import ca.ucalgary.cpsc.ase.factmanager.heuristic.AbstractSolrHeuristicHelper;
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
	
	@Override
	public String getName() {
		return "DF";
	}

	@Override
	public String getFullName() {
		return "Data Flows";
	}

	@Override
	public Map<Integer, ResultItem> match(Query q) throws Exception {
		Map<Integer, ResultItem> aggregated = new HashMap<Integer, ResultItem>();
		for (QueryMethod method : q.getDataFlows().keySet()) {
			AbstractSolrHeuristicHelper fromProcessor = new SolrMethodSignatureQueryProcessor(method);
			Map<Integer, ResultItem> matchedFromMethods = fromProcessor.fetch();
			for (QueryInvocation invocation : q.getDataFlows().get(method)) {
				Map<Integer, ResultItem> testClazzes = null;
				if (invocation instanceof QueryMethod) {					
					AbstractSolrHeuristicHelper toProcessor = new SolrMethodSignatureQueryProcessor((QueryMethod) invocation);
					Map<Integer, ResultItem> matchedToMethods = toProcessor.fetch();
					if (matchedFromMethods.size() > 0 && matchedToMethods.size() > 0) {
						testClazzes = parse(invocationService.getTestsWithMethodToMethodDataFlowPath(
								matchedFromMethods.keySet(), matchedToMethods.keySet()));						
					}
				}
				else {
					Assertion assertion = assertionService.find(((QueryAssertion) invocation).getType());
					if (matchedFromMethods.size() > 0) {
						testClazzes = parse(invocationService.getTestsWithMethodToAssertionDataFlowPath(
								matchedFromMethods.keySet(), assertion));						
					}
				}
				if (testClazzes != null) {
					aggregate(testClazzes, aggregated);					
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
			//TODO score has to match the max(s1 + s2)/2 
			// s1 and s2 are the similarity scores of the two ends of the data flow edge
			// 1. find connected pairs in each graph
			// 2. loop through pairs and find the maximum (s1+s2)/2
			result.setScore(new Double(1));
			results.put(c.getId(), result);
		}
		return results;
	}

	private void aggregate(Map<Integer, ResultItem> current, Map<Integer, ResultItem> aggregated) {
		for (Integer id : current.keySet()) {
			if (aggregated.containsKey(id)) {
				aggregated.get(id).setScore(aggregated.get(id).getScore() + current.get(id).getScore());
			}
			else {
				aggregated.put(id, current.get(id));
			}
		}
	}

	@Override
	public List getMatchingItems(Integer id, Query q) {
		throw new UnsupportedOperationException("Not implemented, use getMatchingDataFlows mehod instead.");
	}
	
	public Map<Method, Set<Invocation>> getMatchingDataFlows(Integer id, Query q) throws Exception {
		Map<Method, Set<Invocation>> aggregated = new HashMap<Method, Set<Invocation>>();
		for (QueryMethod method : q.getDataFlows().keySet()) {
			AbstractSolrHeuristicHelper fromProcessor = new SolrMethodSignatureQueryProcessor(method);
			Map<Integer, ResultItem> matchedFromMethods = fromProcessor.fetch();

			for (QueryInvocation invocation : q.getDataFlows().get(method)) {
				List dataFlows = null;
				if (invocation instanceof QueryMethod) {					
					AbstractSolrHeuristicHelper toProcessor = new SolrMethodSignatureQueryProcessor((QueryMethod) invocation);
					Map<Integer, ResultItem> matchedToMethods = toProcessor.fetch();
					if (matchedFromMethods.size() > 0 && matchedToMethods.size() > 0) {
						dataFlows = invocationService.getMatchingMethodToMethodDataFlows(
								id, matchedFromMethods.keySet(), matchedToMethods.keySet());						
					}
				}
				else {
					Assertion assertion = assertionService.find(((QueryAssertion) invocation).getType());
					if (matchedFromMethods.size() > 0) {
						dataFlows = invocationService.getMatchingMethodToAssertionDataFlows(
								id, matchedFromMethods.keySet(), assertion);						
					}
				}
				if (dataFlows != null) {
					aggregate(dataFlows, aggregated);					
				}
			}
		}
		return aggregated;
	}

	private void aggregate(List current, Map<Method, Set<Invocation>> aggregated) {
		Iterator i = current.iterator();
		while (i.hasNext()) {
			Object[] dataFlow = (Object[]) i.next();
			Method method = (Method) dataFlow[0];
			Invocation invocation = (Invocation) dataFlow[1];
			if (aggregated.keySet().contains(method)) {
				aggregated.get(method).add(invocation);
			}
			else {
				Set<Invocation> invocations = new HashSet<Invocation>();
				invocations.add(invocation);
				aggregated.put(method, invocations);
			}
		}
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
