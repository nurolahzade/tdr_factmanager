package ca.ucalgary.cpsc.ase.QueryManager;

import java.util.Set;

import ca.ucalgary.cpsc.ase.FactManager.entity.CodeEntity;
import ca.ucalgary.cpsc.ase.FactManager.entity.TestMethod;

public class ResultItem {

	protected TestMethod target;
	protected double score;
	protected Set<CodeEntity> relevantItems;
	
	public TestMethod getTarget() {
		return target;
	}
	
	public void setTarget(TestMethod target) {
		this.target = target;
	}
	
	public double getScore() {
		return score;
	}
	
	public void setScore(double score) {
		this.score = score;
	}
	
	public Set<CodeEntity> getRelevantItems() {
		return relevantItems;
	}
	
	public void setRelevantItems(Set<CodeEntity> relevantItems) {
		this.relevantItems = relevantItems;
	}
		
}
