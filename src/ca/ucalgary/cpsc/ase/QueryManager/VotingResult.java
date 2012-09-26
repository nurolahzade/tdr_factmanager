package ca.ucalgary.cpsc.ase.QueryManager;

import java.util.HashSet;
import java.util.Set;

public class VotingResult {

	private double score;
	private Set<Heuristic> heuristics;
	
	public VotingResult() {
		score = 0;
		heuristics = new HashSet<Heuristic>();
	}
	
	public double getScore() {
		return score;
	}
	
	public void setScore(double score) {
		this.score = score;
	}
	
	public Set<Heuristic> getHeuristics() {
		return heuristics;
	}
	
	public double add(Heuristic heuristic, double score) {
		this.heuristics.add(heuristic);
		this.score += score;
		return this.score;
	}
	
}
