package ca.ucalgary.cpsc.ase.QueryManager;

import java.util.ArrayList;
import java.util.List;

import ca.ucalgary.cpsc.ase.QueryManager.query.QueryAssertion;
import ca.ucalgary.cpsc.ase.QueryManager.query.QueryAssertionParameter;
import ca.ucalgary.cpsc.ase.QueryManager.query.QueryElement;
import ca.ucalgary.cpsc.ase.QueryManager.query.QueryException;
import ca.ucalgary.cpsc.ase.QueryManager.query.QueryMethod;
import ca.ucalgary.cpsc.ase.QueryManager.query.QueryReference;
import ca.ucalgary.cpsc.ase.QueryManager.query.QueryTestClass;
import ca.ucalgary.cpsc.ase.QueryManager.query.QueryTestMethod;

public class Query {

	private QueryTestMethod testMethod;
	private QueryTestClass testClass;
	private List<QueryReference> references;
	private List<QueryMethod> invocations;
	private List<QueryException> exceptions;
	private List<QueryAssertion> assertions;
	private List<QueryAssertionParameter> parameters;
		
	public QueryTestMethod getTestMethod() {
		return testMethod;
	}

	public void setTestMethod(QueryTestMethod testMethod) {
		this.testMethod = testMethod;
	}

	public QueryTestClass getTestClass() {
		return testClass;
	}

	public void setTestClass(QueryTestClass testClass) {
		this.testClass = testClass;
	}

	public List<QueryMethod> getInvocations() {
		return invocations;
	}

	public void setInvocations(List<QueryMethod> invocations) {
		this.invocations = invocations;
	}
	
	public void add(QueryMethod method) {
		if (invocations == null) {
			invocations = new ArrayList<QueryMethod>();
		}
		else
			if (invocations.contains(method)) {
				return;
			}
		invocations.add(method);			
	}

	public List<QueryReference> getReferences() {
		return references;
	}

	public void setReferences(List<QueryReference> references) {
		this.references = references;
	}
	
	public void add(QueryReference reference) {
		if (references == null) {
			references = new ArrayList<QueryReference>();
		}
		else
			if (references.contains(reference)) {
				return;
			}
		references.add(reference);
	}
	
	public List<QueryException> getExceptions() {
		return exceptions;
	}

	public void setExceptions(List<QueryException> exceptions) {
		this.exceptions = exceptions;
	}
	
	public void add(QueryException exception) {
		if (exceptions == null) {
			exceptions = new ArrayList<QueryException>();
		}
		else
			if (exceptions.contains(exception)) {
				return;
			}
		exceptions.add(exception);
	}

	public List<QueryAssertion> getAssertions() {
		return assertions;
	}

	public void setAssertions(List<QueryAssertion> assertions) {
		this.assertions = assertions;
	}
	
	public void add(QueryAssertion assertion) {
		if (assertions == null) {
			assertions = new ArrayList<QueryAssertion>();
		}
		else
			if (assertions.contains(assertion)) {
				return;
			}
		assertions.add(assertion);
	}
		
	public List<QueryAssertionParameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<QueryAssertionParameter> parameters) {
		this.parameters = parameters;
	}
	
	public void add(QueryAssertionParameter parameter) {
		if (parameters == null) {
			parameters = new ArrayList<QueryAssertionParameter>();
		}
		else
			if (parameters.contains(parameter)) {
				return;
			}
		parameters.add(parameter);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append(testMethod.getCaption());
		builder.append("=");
		builder.append(testMethod);
		builder.append(", ");
		
		builder.append(testClass.getCaption());
		builder.append("=");
		builder.append(testClass);
		
		builder.append(toString(references));
		builder.append(toString(invocations));
		builder.append(toString(exceptions));
		builder.append(toString(assertions));
		builder.append(toString(parameters));
		
		return builder.toString();
	}
	
	private String toString(List elements) {
		StringBuilder builder = new StringBuilder();
		List<QueryElement> items = (List<QueryElement>) elements;
		if (items != null && items.size() > 0) {
			builder.append(", ");
			builder.append(items.get(0).getCaption());
			builder.append("={");
			for (QueryElement item : items) {
				builder.append(item);
				builder.append(", ");
			}
			builder.append("}");
		}
		return builder.toString();
	}
}
