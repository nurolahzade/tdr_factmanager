package ca.ucalgary.cpsc.ase.QueryManager;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class VotingHeuristicManager extends HeuristicManager {

	private Map<Integer, VotingResult> scores;

	public VotingHeuristicManager() throws Exception {
		super();
		scores = new LinkedHashMap<Integer, VotingResult>(); 
	}
	
	public Map<Integer, VotingResult> match(Query q) throws Exception {
		for (Heuristic heuristic : heuristics) {
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
			result = new VotingResult();
			scores.put(id, result);
		}
		result.add(heuristic, item.getScore());
	}

}
