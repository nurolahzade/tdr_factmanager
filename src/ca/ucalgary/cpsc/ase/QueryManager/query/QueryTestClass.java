package ca.ucalgary.cpsc.ase.QueryManager.query;

import ca.ucalgary.cpsc.ase.FactManager.entity.ObjectType;

public class QueryTestClass implements QueryElement {

	private String name;
	private String packageName;
	private ObjectType type;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPackageName() {
		return packageName;
	}
	
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	public String getQualifiedName() {
		return (packageName != null ? packageName + "." : "") + name; 
	}

	public ObjectType getType() {
		return type;
	}

	public void setType(ObjectType type) {
		this.type = type;
	}
	
}
