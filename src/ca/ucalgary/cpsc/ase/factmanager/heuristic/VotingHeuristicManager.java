package ca.ucalgary.cpsc.ase.factmanager.heuristic;

import java.net.MalformedURLException;
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
import ca.ucalgary.cpsc.ase.common.heuristic.Heuristic;
import ca.ucalgary.cpsc.ase.common.heuristic.HeuristicManager;
import ca.ucalgary.cpsc.ase.common.heuristic.ResultItem;
import ca.ucalgary.cpsc.ase.common.heuristic.VotingResult;
import ca.ucalgary.cpsc.ase.common.query.Query;
import ca.ucalgary.cpsc.ase.factmanager.heuristic.impl.AssertionHeuristic;
import ca.ucalgary.cpsc.ase.factmanager.heuristic.impl.BestFitInvocationHeuristic;
import ca.ucalgary.cpsc.ase.factmanager.heuristic.impl.DataFlowHeuristic;
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
	
	protected Map<String, Heuristic> heuristics;
			
	private Map<Integer, VotingResult> scores;

	public VotingHeuristicManager() throws Exception {
		super();
	}
	
	@PostConstruct
	protected void init() throws MalformedURLException {
		heuristics = new HashMap<String, Heuristic>();
		heuristics.put(referenceHeuristic.getName(), referenceHeuristic);
		heuristics.put(invocationHeuristic.getName(), invocationHeuristic);
		heuristics.put(bestFitInvocationHeuristic.getName(), bestFitInvocationHeuristic);
		heuristics.put(assertionHeuristic.getName(), assertionHeuristic);
		heuristics.put(dataFlowHeuristic.getName(), dataFlowHeuristic);
		heuristics.put(keywordHeuristic.getName(), keywordHeuristic);		
	}

	@Override
	public Map<Integer, VotingResult> match(Query q) throws Exception {
		scores = new LinkedHashMap<Integer, VotingResult>(); 
		for (Heuristic heuristic : heuristics.values()) {
			Map<Integer, ResultItem> results = heuristic.match(q);
			countVotes(results, heuristic);
		}
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
 
}
