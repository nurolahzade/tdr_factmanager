package ca.ucalgary.cpsc.ase.QueryManager;

import java.util.List;

import ca.ucalgary.cpsc.ase.QueryManager.query.QueryAssertion;
import ca.ucalgary.cpsc.ase.QueryManager.query.QueryMethod;
import ca.ucalgary.cpsc.ase.QueryManager.query.QueryReference;

public class Query {

	private List<QueryReference> references;
	private List<QueryMethod> invocations;
	private List<QueryAssertion> assertions;
	
	public List<QueryMethod> getInvocations() {
		return invocations;
	}

	public void setInvocations(List<QueryMethod> invocations) {
		this.invocations = invocations;
	}

	public List<QueryReference> getReferences() {
		return references;
	}

	public void setReferences(List<QueryReference> references) {
		this.references = references;
	}

	public List<QueryAssertion> getAssertions() {
		return assertions;
	}

	public void setAssertions(List<QueryAssertion> assertions) {
		this.assertions = assertions;
	}
			
	
}
