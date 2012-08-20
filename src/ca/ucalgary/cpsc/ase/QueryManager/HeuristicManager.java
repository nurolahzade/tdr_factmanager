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
	
	public abstract Map<Integer, Set<Heuristic>> match(Query q) throws Exception;
	
	protected Map<Integer, Set<Heuristic>> sort(Map<Integer, Set<Heuristic>> unsorted) {
		List<Entry<Integer, Set<Heuristic>>> list = new LinkedList<Entry<Integer, Set<Heuristic>>>(unsorted.entrySet());
		
		Collections.sort(list, new Comparator<Entry<Integer, Set<Heuristic>>>() {
            public int compare(Entry<Integer, Set<Heuristic>> o1, Entry<Integer, Set<Heuristic>> o2) {
	           return new Integer(o2.getValue().size()).compareTo(o1.getValue().size()) ;
            }
		});

		Map<Integer, Set<Heuristic>> sortedMap = new LinkedHashMap<Integer, Set<Heuristic>>();
		for (Iterator<Entry<Integer, Set<Heuristic>>> it = list.iterator(); it.hasNext();) {
		     Entry<Integer, Set<Heuristic>> entry = it.next();
		     sortedMap.put(entry.getKey(), entry.getValue());
		}
		
		return sortedMap;				
	}
	
}
