package ca.ucalgary.cpsc.ase.QueryManager.query;

public abstract class QueryElement {
	
	protected boolean resolved;
	
	public abstract String getCaption();
	
	public void setResolved(boolean resolved) {
		this.resolved = resolved;
	}
	
	public boolean isResolved() {
		return resolved;
	}

}
