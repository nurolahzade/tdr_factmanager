package ca.ucalgary.cpsc.ase.QueryManager;

import java.util.List;

import ca.ucalgary.cpsc.ase.QueryManager.query.QueryAssertion;
import ca.ucalgary.cpsc.ase.QueryManager.query.QueryAssertionParameter;
import ca.ucalgary.cpsc.ase.QueryManager.query.QueryException;
import ca.ucalgary.cpsc.ase.QueryManager.query.QueryMethod;
import ca.ucalgary.cpsc.ase.QueryManager.query.QueryReference;

public class Query {

	private String methodName;
	private String className;
	private List<QueryReference> references;
	private List<QueryMethod> invocations;
	private List<QueryException> exceptions;
	private List<QueryAssertion> assertions;
	private List<QueryAssertionParameter> parameters;
		
	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

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
	
	public List<QueryException> getExceptions() {
		return exceptions;
	}

	public void setExceptions(List<QueryException> exceptions) {
		this.exceptions = exceptions;
	}

	public List<QueryAssertion> getAssertions() {
		return assertions;
	}

	public void setAssertions(List<QueryAssertion> assertions) {
		this.assertions = assertions;
	}
		
	public List<QueryAssertionParameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<QueryAssertionParameter> parameters) {
		this.parameters = parameters;
	}

	
}
