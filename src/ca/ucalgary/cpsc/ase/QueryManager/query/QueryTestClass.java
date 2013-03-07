package ca.ucalgary.cpsc.ase.QueryManager.query;

import ca.ucalgary.cpsc.ase.common.entity.ObjectType;

public class QueryTestClass extends QueryElement {

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
		return (packageName != null && packageName.length() > 0 ? packageName + "." : "") + name; 
	}

	public ObjectType getType() {
		return type;
	}

	public void setType(ObjectType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getQualifiedName());
		builder.append(" [");
		builder.append(type);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public String getCaption() {
		return "Test Class";
	}
	
}
