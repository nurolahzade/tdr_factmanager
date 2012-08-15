package ca.ucalgary.cpsc.ase.QueryManager.heuristic;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ca.ucalgary.cpsc.ase.FactManager.service.TestMethodService;
import ca.ucalgary.cpsc.ase.QueryManager.Query;
import ca.ucalgary.cpsc.ase.QueryManager.ResultItem;

public class BestFitInvocationHeuristic extends InvocationHeuristic {

	@Override
	public Map<Integer, ResultItem> match(Query q) {
		
		Map<Integer, ResultItem> results = super.match(q);
		
		normalize(results);
		
		return sort(results);
	}

	private void normalize(Map<Integer, ResultItem> results) {
		TestMethodService service = new TestMethodService();
		for (ResultItem result : results.values()) {
			result.setScore(result.getScore() / service.getInvocationsCount(result.getTarget()));
		}		
	}

	protected Map<Integer, ResultItem> sort(Map<Integer, ResultItem> unsorted) {
		List<Entry<Integer, ResultItem>> list = new LinkedList<Entry<Integer, ResultItem>>(unsorted.entrySet());
		
		Collections.sort(list, new Comparator<Entry<Integer, ResultItem>>() {
            public int compare(Entry<Integer, ResultItem> o1, Entry<Integer, ResultItem> o2) {
	           return o2.getValue().getScore().compareTo(o1.getValue().getScore());
            }
		});

		Map<Integer, ResultItem> sortedMap = new LinkedHashMap<Integer, ResultItem>();
		for (Iterator<Entry<Integer, ResultItem>> it = list.iterator(); it.hasNext();) {
		     Entry<Integer, ResultItem> entry = it.next();
		     sortedMap.put(entry.getKey(), entry.getValue());
		}
		
		return sortedMap;				
	}
	
	
}
