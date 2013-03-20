package ca.ucalgary.cpsc.ase.factmanager.heuristic;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ca.ucalgary.cpsc.ase.common.heuristic.VotingResult;

public abstract class AbstractHeuristicManager {
	
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
