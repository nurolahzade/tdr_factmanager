package ca.ucalgary.cpsc.ase.QueryManager.query;

import ca.ucalgary.cpsc.ase.FactManager.entity.AssertionType;

public class QueryAssertion implements QueryInvocation {

	private AssertionType type;

	public AssertionType getType() {
		return type;
	}

	public void setType(AssertionType type) {
		this.type = type;
	}
		
}
