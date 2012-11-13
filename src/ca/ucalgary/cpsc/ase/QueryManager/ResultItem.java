package ca.ucalgary.cpsc.ase.QueryManager;

import java.util.Set;

import ca.ucalgary.cpsc.ase.FactManager.entity.Clazz;
import ca.ucalgary.cpsc.ase.FactManager.entity.CodeEntity;

public class ResultItem {

	protected Clazz target;
	protected Double score;
	protected Set<CodeEntity> relevantItems;
	
	public Clazz getTarget() {
		return target;
	}
	
	public void setTarget(Clazz target) {
		this.target = target;
	}
	
	public Double getScore() {
		return score;
	}
	
	public void setScore(Double score) {
		this.score = score;
	}
	
	public Set<CodeEntity> getRelevantItems() {
		return relevantItems;
	}
	
	public void setRelevantItems(Set<CodeEntity> relevantItems) {
		this.relevantItems = relevantItems;
	}
		
}
