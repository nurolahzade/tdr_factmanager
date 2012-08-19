package ca.ucalgary.cpsc.ase.QueryManager.query;

public class QueryAssertionParameter implements QueryElement {

	private QueryMethod method;
	private QueryAssertion assertion;

	public QueryMethod getMethod() {
		return method;
	}
	
	public void setMethod(QueryMethod method) {
		this.method = method;
	}
	
	public QueryAssertion getAssertion() {
		return assertion;
	}
	
	public void setAssertion(QueryAssertion assertion) {
		this.assertion = assertion;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("QueryAssertionParameter [method=");
		builder.append(method);
		builder.append(", assertion=");
		builder.append(assertion);
		builder.append("]");
		return builder.toString();
	}
		 	
}
