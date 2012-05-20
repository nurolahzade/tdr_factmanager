package ca.ucalgary.cpsc.ase.QueryManager;

import java.util.List;

import ca.ucalgary.cpsc.ase.QueryManager.query.QueryMethod;

public class Query {

	private List<QueryMethod> invocations;

	public List<QueryMethod> getInvocations() {
		return invocations;
	}

	public void setInvocations(List<QueryMethod> invocations) {
		this.invocations = invocations;
	}
	
	
}
