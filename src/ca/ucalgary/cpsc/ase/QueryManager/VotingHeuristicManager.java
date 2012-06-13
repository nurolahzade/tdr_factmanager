package ca.ucalgary.cpsc.ase.QueryManager;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class VotingHeuristicManager extends HeuristicManager {

	private Map<Integer, Double> scores;

	public VotingHeuristicManager() throws Exception {
		super();
		scores = new LinkedHashMap<Integer, Double>(); 
	}
	
	public Map<Integer, Double> match(Query q) throws Exception {
		for (Heuristic heuristic : heuristics) {
			Map<Integer, ResultItem> results = heuristic.match(q);
			countVotes(results);
		}
		return sort(scores);
	}

	private void countVotes(Map<Integer, ResultItem> results) {
		Iterator<Entry<Integer, ResultItem>> iterator = results.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Integer, ResultItem> entry = iterator.next();
			Integer id = entry.getKey();
			countVote(id);
		}
	}

	private void countVote(Integer id) {
		Double votes = 0.0;
		if (scores.containsKey(id)) {
			votes += scores.get(id);
		}
		scores.put(id, votes + 1);		
	}

}
