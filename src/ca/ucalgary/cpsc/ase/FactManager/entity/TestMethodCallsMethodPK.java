package ca.ucalgary.cpsc.ase.FactManager.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the testmethod_calls_method database table.
 * 
 */
@Embeddable
public class TestMethodCallsMethodPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="test_method_id")
	private Integer testMethodId;

	@Column(name="method_id")
	private Integer methodId;

    public TestMethodCallsMethodPK() {
    }
    
	public Integer getTestMethodId() {
		return this.testMethodId;
	}
	
	public void setTestMethodId(Integer testMethodId) {
		this.testMethodId = testMethodId;
	}
	
	public Integer getMethodId() {
		return this.methodId;
	}
	
	public void setMethodId(Integer methodId) {
		this.methodId = methodId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TestMethodCallsMethodPK)) {
			return false;
		}
		TestMethodCallsMethodPK castOther = (TestMethodCallsMethodPK)other;
		return 
			this.testMethodId.equals(castOther.testMethodId)
			&& this.methodId.equals(castOther.methodId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.testMethodId.hashCode();
		hash = hash * prime + this.methodId.hashCode();
		
		return hash;
    }
}