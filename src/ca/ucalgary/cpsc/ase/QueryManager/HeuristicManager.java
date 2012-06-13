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
	
	public abstract Map<Integer, Double> match(Query q) throws Exception;
	
	protected Map<Integer, Double> sort(Map<Integer, Double> unsorted) {
		List<Entry<Integer, Double>> list = new LinkedList<Entry<Integer, Double>>(unsorted.entrySet());
		
		Collections.sort(list, new Comparator<Entry<Integer, Double>>() {
            public int compare(Entry<Integer, Double> o1, Entry<Integer, Double> o2) {
	           return o2.getValue().compareTo(o1.getValue()) ;
            }
		});

		Map<Integer, Double> sortedMap = new LinkedHashMap<Integer, Double>();
		for (Iterator<Entry<Integer, Double>> it = list.iterator(); it.hasNext();) {
		     Entry<Integer, Double> entry = it.next();
		     sortedMap.put(entry.getKey(), entry.getValue());
		}
		
		return sortedMap;				
	}
	
}
