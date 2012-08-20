package ca.ucalgary.cpsc.ase.QueryManager;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class VotingHeuristicManager extends HeuristicManager {

	private Map<Integer, Set<Heuristic>> scores;

	public VotingHeuristicManager() throws Exception {
		super();
		scores = new LinkedHashMap<Integer, Set<Heuristic>>(); 
	}
	
	public Map<Integer, Set<Heuristic>> match(Query q) throws Exception {
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
			countVote(id, heuristic);
		}
	}

	private void countVote(Integer id, Heuristic heuristic) {
		Set<Heuristic> votes;
		if (scores.containsKey(id)) {
			votes = scores.get(id);
		}
		else {
			votes = new HashSet<Heuristic>();
		}
		votes.add(heuristic);
		scores.put(id, votes);
	}

}
