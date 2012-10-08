package ca.ucalgary.cpsc.ase.QueryManager;

import java.util.HashSet;
import java.util.Set;

import ca.ucalgary.cpsc.ase.FactManager.entity.TestMethod;

public class VotingResult {

	private Integer id;
	private Integer rank;
	private Double score;
	private String fqn;
	private Set<Heuristic> heuristics;
	
	public VotingResult(Integer id, String fqn) {
		this.id = id;
		this.fqn = fqn;
		this.rank = null;
		this.score = new Double(0);
		this.heuristics = new HashSet<Heuristic>();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFqn() {
		return fqn;
	}

	public void setFqn(String fqn) {
		this.fqn = fqn;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Double getScore() {
		return score;
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
