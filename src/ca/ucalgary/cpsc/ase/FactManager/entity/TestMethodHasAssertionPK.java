package ca.ucalgary.cpsc.ase.FactManager.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the testmethod_has_assertion database table.
 * 
 */
@Embeddable
public class TestMethodHasAssertionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="assertion_id")
	private Integer assertionId;

	@Column(name="test_method_id")
	private Integer testMethodId;

    public TestMethodHasAssertionPK() {
    }
    
	public Integer getAssertionId() {
		return this.assertionId;
	}
	
	public void setAssertionId(Integer assertionId) {
		this.assertionId = assertionId;
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
		if (!(other instanceof TestMethodHasAssertionPK)) {
			return false;
		}
		TestMethodHasAssertionPK castOther = (TestMethodHasAssertionPK)other;
		return 
			this.assertionId.equals(castOther.assertionId)
			&& this.testMethodId.equals(castOther.testMethodId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.assertionId.hashCode();
		hash = hash * prime + this.testMethodId.hashCode();
		
		return hash;
    }
}