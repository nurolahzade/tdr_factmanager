package ca.ucalgary.cpsc.ase.QueryManager;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import ca.ucalgary.cpsc.ase.QueryManager.heuristic.AssertionHeuristic;
import ca.ucalgary.cpsc.ase.QueryManager.heuristic.AssertionParameterHeuristic;
import ca.ucalgary.cpsc.ase.QueryManager.heuristic.BestFitInvocationHeuristic;
import ca.ucalgary.cpsc.ase.QueryManager.heuristic.InvocationHeuristic;
import ca.ucalgary.cpsc.ase.QueryManager.heuristic.ReferenceHeuristic;
import ca.ucalgary.cpsc.ase.QueryManager.heuristic.SolrNamesAndFQNsHeuristic;

public abstract class HeuristicManager {
	
	protected ReferenceHeuristic referenceHeuristic;
	protected InvocationHeuristic invocationHeuristic;
	protected BestFitInvocationHeuristic bestFitInvocationHeuristic;
	protected AssertionHeuristic assertionHeuristic;
	protected AssertionParameterHeuristic assertionParameterHeuristic;
	protected SolrHeuristic keywordHeuristic;
	
	protected Set<Heuristic> heuristics;
		
	public HeuristicManager() throws Exception {
		referenceHeuristic = new ReferenceHeuristic();
		invocationHeuristic = new InvocationHeuristic();
		bestFitInvocationHeuristic = new BestFitInvocationHeuristic();
		assertionHeuristic = new AssertionHeuristic();
		assertionParameterHeuristic = new AssertionParameterHeuristic();
		keywordHeuristic = new SolrNamesAndFQNsHeuristic();
		
		heuristics = new HashSet<Heuristic>();
		heuristics.add(referenceHeuristic);
		heuristics.add(invocationHeuristic);
		heuristics.add(bestFitInvocationHeuristic);
		heuristics.add(assertionHeuristic);
		heuristics.add(assertionParameterHeuristic);
		heuristics.add(keywordHeuristic);
	}
	
	public abstract Map<Integer, VotingResult> match(Query q) throws Exception;
	
	protected Map<Integer, VotingResult> sort(Map<Integer, VotingResult> unsorted) {
		List<Entry<Integer, VotingResult>> list = new LinkedList<Entry<Integer, VotingResult>>(unsorted.entrySet());
		
		Collections.sort(list, new Comparator<Entry<Integer, VotingResult>>() {
            public int compare(Entry<Integer, VotingResult> o1, Entry<Integer, VotingResult> o2) {
	           return new Double(o2.getValue().getScore()).compareTo(o1.getValue().getScore()) ;
            }
		});

		Map<Integer, VotingResult> sortedMap = new LinkedHashMap<Integer, VotingResult>();
		int rank = 0;
		for (Iterator<Entry<Integer, VotingResult>> it = list.iterator(); it.hasNext();) {
		     Entry<Integer, VotingResult> entry = it.next();
		     VotingResult result = entry.getValue();
		     result.setRank(++rank);
		     sortedMap.put(entry.getKey(), result);
		}
		
		return sortedMap;				
	}
	
}
