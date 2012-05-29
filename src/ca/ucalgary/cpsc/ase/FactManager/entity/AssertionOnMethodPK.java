package ca.ucalgary.cpsc.ase.FactManager.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the Assertion_on_Method database table.
 * 
 */
@Embeddable
public class AssertionOnMethodPK implements Serializable {
	
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="assertion_id")
	private Integer assertionId;

	@Column(name="method_id")
	private Integer methodId;

	@Column(name="test_method_id")
	private Integer testMethodId;

    public AssertionOnMethodPK() {
    }
    
	public Integer getAssertionId() {
		return this.assertionId;
	}
	
	public void setAssertionId(Integer assertionId) {
		this.assertionId = assertionId;
	}
	
	public Integer getMethodId() {
		return this.methodId;
	}
	
	public void setMethodId(Integer methodId) {
		this.methodId = methodId;
	}
	
	public Integer getTestMethodId() {
		return this.testMethodId;
	}
	
	public void setTestMethodId(Integer testMethodId) {
		this.testMethodId = testMethodId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AssertionOnMethodPK)) {
			return false;
		}
		AssertionOnMethodPK castOther = (AssertionOnMethodPK)other;
		return 
			this.assertionId.equals(castOther.assertionId)
			&& this.methodId.equals(castOther.methodId)
			&& this.testMethodId.equals(castOther.testMethodId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.assertionId.hashCode();
		hash = hash * prime + this.methodId.hashCode();
		hash = hash * prime + this.testMethodId.hashCode();
		
		return hash;
    }
}