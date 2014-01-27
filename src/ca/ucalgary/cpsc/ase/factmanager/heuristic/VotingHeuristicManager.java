package ca.ucalgary.cpsc.ase.factmanager.heuristic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import ca.ucalgary.cpsc.ase.common.ServiceDirectory;
import ca.ucalgary.cpsc.ase.common.entity.Invocation;
import ca.ucalgary.cpsc.ase.common.entity.Method;
import ca.ucalgary.cpsc.ase.common.heuristic.Heuristic;
import ca.ucalgary.cpsc.ase.common.heuristic.HeuristicManager;
import ca.ucalgary.cpsc.ase.common.heuristic.ResultItem;
import ca.ucalgary.cpsc.ase.common.heuristic.VotingResult;
import ca.ucalgary.cpsc.ase.common.query.Query;
import ca.ucalgary.cpsc.ase.factmanager.heuristic.impl.AssertionHeuristic;
import ca.ucalgary.cpsc.ase.factmanager.heuristic.impl.BestFitInvocationHeuristic;
import ca.ucalgary.cpsc.ase.factmanager.heuristic.impl.DataFlowHeuristic;
import ca.ucalgary.cpsc.ase.factmanager.heuristic.impl.InterfaceKeywordHeuristic;
import ca.ucalgary.cpsc.ase.factmanager.heuristic.impl.InvocationHeuristic;
import ca.ucalgary.cpsc.ase.factmanager.heuristic.impl.KeywordHeuristic;
import ca.ucalgary.cpsc.ase.factmanager.heuristic.impl.ReferenceHeuristic;

@Stateless(name="VotingHeuristicManager", mappedName=ServiceDirectory.HEURISTIC_MANAGER)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class VotingHeuristicManager extends AbstractHeuristicManager implements HeuristicManager {

	@EJB protected ReferenceHeuristic referenceHeuristic;
	@EJB protected InvocationHeuristic invocationHeuristic;
	@EJB protected BestFitInvocationHeuristic bestFitInvocationHeuristic;
	@EJB protected AssertionHeuristic assertionHeuristic;
	@EJB protected DataFlowHeuristic dataFlowHeuristic;
	@EJB protected KeywordHeuristic keywordHeuristic;
	@EJB protected InterfaceKeywordHeuristic interfaceKeywordHeuristic;
	
	protected Map<String, Heuristic> heuristics;
			
	private Map<Integer, VotingResult> scores;

	public VotingHeuristicManager() throws Exception {
		super();
	}
	
	@PostConstruct
	protected void init() {
		heuristics = new HashMap<String, Heuristic>();
//		heuristics.put(referenceHeuristic.getName(), referenceHeuristic);
//		heuristics.put(invocationHeuristic.getName(), invocationHeuristic);
		heuristics.put(dataFlowHeuristic.getName(), dataFlowHeuristic);
		heuristics.put(keywordHeuristic.getName(), keywordHeuristic);		
	}

	@Override
	public Map<Integer, VotingResult> match(Query q) throws Exception {
		return match(q, heuristics.keySet());
	}

	@Override
	public Map<Integer, VotingResult> match(Query q, Set<String> filter)
			throws Exception {
		scores = new LinkedHashMap<Integer, VotingResult>(); 
		for (Heuristic heuristic : heuristics.values()) {
			if (filter.contains(heuristic.getName())) {
				Map<Integer, ResultItem> results = heuristic.match(q);
				countVotes(results, heuristic);				
			}
		}
		return sort(scores);
	}	
	
	@Override
	public Map<Integer, VotingResult> simulateInterfaceBasedRetrieval(Query q, String target) 
			throws Exception {
		scores = new LinkedHashMap<Integer, VotingResult>();
		interfaceKeywordHeuristic.setTarget(target);
		Map<Integer, ResultItem> results = interfaceKeywordHeuristic.match(q);
		countVotes(results, interfaceKeywordHeuristic);				
		return sort(scores);		
	}
	
	private void countVotes(Map<Integer, ResultItem> results, Heuristic heuristic) {
		Iterator<Entry<Integer, ResultItem>> iterator = results.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Integer, ResultItem> entry = iterator.next();
			Integer id = entry.getKey();
			ResultItem item = entry.getValue();
			countVote(id, item, heuristic);
		}
	}

	private void countVote(Integer id, ResultItem item, Heuristic heuristic) {
		VotingResult result;
		if (scores.containsKey(id)) {
			result = scores.get(id);
		}
		else {
			result = new VotingResult(id, item.getTarget().getFqn());
			scores.put(id, result);
		}
		result.add(heuristic, item);
	}
	
	@Override
	public String getFullName(String heuristic) {
		return heuristics.get(heuristic).getFullName();
	}
	
	@Override
	public List getMatchingItems(Integer id, Query q, String heuristic) {
		return heuristics.get(heuristic).getMatchingItems(id, q);
	}
	
	@Override
	public Map<String, Set<String>> getMatchingDataFlows(Integer id, Query q) throws Exception {
		Map<Method, Set<Invocation>> entitytMap = dataFlowHeuristic.getMatchingDataFlows(id, q);
		Map<String, Set<String>> stringMap = new HashMap<String, Set<String>>();
		for (Method method : entitytMap.keySet()) {
			String stringMethod = method.toString();
			stringMap.put(stringMethod, new HashSet<String>());
			for (Invocation invocation : entitytMap.get(method)) {
				stringMap.get(stringMethod).add(invocation.toString());
			}
		}
		return stringMap;
	}

}
