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

import ca.ucalgary.cpsc.ase.FactManager.entity.TestMethod;

public abstract class DatabaseHeuristic implements Heuristic {

	public Map<Integer, ResultItem> match(Query q) {
		Map<Integer, ResultItem> results;
		
		Set resolved = resolve(q);
		
		if (resolved.size() > 0) {
			List dbResults = retrieve(resolved);			
			results = parse(dbResults);			
		}
		else {
			results = new LinkedHashMap<Integer, ResultItem>();
		}
		
		normalize(q, results);
		
		return sort(results);		
	}
	
	protected abstract Set resolve(Query q);
	
	protected abstract List retrieve(Set resolved);
	
	protected abstract void normalize(Query q, Map<Integer, ResultItem> results);
	
	protected Map<Integer, ResultItem> parse(List rawResults) {
		Map<Integer, ResultItem> results = new LinkedHashMap<Integer, ResultItem>();
		
		System.out.print(this.getClass().getName() + ": ");
		for (int i = 0; i < rawResults.size(); ++i) {
			ResultItem result = new ResultItem();
			Object[] databaseResult = (Object[]) rawResults.get(i);
			TestMethod tm = (TestMethod) databaseResult[0];
			result.setTarget(tm);
			result.setScore(((Long) databaseResult[1]).doubleValue());
			results.put(tm.getId(), result);
			System.out.print(tm.getId() + ", ");
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
