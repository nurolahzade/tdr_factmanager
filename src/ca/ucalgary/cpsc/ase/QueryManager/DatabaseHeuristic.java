package ca.ucalgary.cpsc.ase.QueryManager;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import ca.ucalgary.cpsc.ase.FactManager.entity.Clazz;

public abstract class DatabaseHeuristic implements Heuristic {

	public Map<Integer, ResultItem> match(Query q) {
		Map<Integer, ResultItem> results;
		Map<Integer, ResultItem> sorted;
		
		Set resolved = resolve(q);
		
		if (resolved.size() > 0) {
			List dbResults = retrieve(resolved);			
			results = parse(dbResults);			
			sorted = sort(results);
			normalize(q, sorted);
		}
		else {
			sorted = new LinkedHashMap<Integer, ResultItem>();
		}
				
		return sorted;		
	}
	
	protected abstract Set resolve(Query q);
	
	protected abstract List retrieve(Set resolved);
	
	protected abstract long getNormalizationFactor(Query q, ResultItem item);
	
	protected void normalize(Query q, Map<Integer, ResultItem> results) {
		if (results.size() == 0)
			return;
		
		Double max = 0.0;
		for (ResultItem result : results.values()) {
			Double score = result.getScore() / getNormalizationFactor(q, result);
			if (score > max)
				max = score;
			result.setScore(score);
		}
		
		for (ResultItem result : results.values()) {
			result.setScore(result.getScore() / max);
		}
	}
	
	protected Map<Integer, ResultItem> parse(List rawResults) {
		Map<Integer, ResultItem> results = new LinkedHashMap<Integer, ResultItem>();
		
		System.out.print(this.getClass().getName() + ": ");
		for (int i = 0; i < rawResults.size(); ++i) {
			ResultItem result = new ResultItem();
			Object[] databaseResult = (Object[]) rawResults.get(i);
			Clazz c = (Clazz) databaseResult[0];
			result.setTarget(c);
			result.setScore(((Long) databaseResult[1]).doubleValue());
			results.put(c.getId(), result);
			System.out.print(c.getId() + ", ");
		}
		System.out.println();
		return results;
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
