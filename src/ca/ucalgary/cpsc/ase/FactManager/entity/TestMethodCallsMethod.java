package ca.ucalgary.cpsc.ase.FactManager.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the testmethod_calls_method database table.
 * 
 */
@Entity
@Table(name="TestMethod_calls_Method")
@NamedQuery(name="findTestMethodCallsMethod", query="SELECT tmcm FROM TestMethodCallsMethod tmcm " +
		"WHERE tmcm.testMethod = :testMethod AND tmcm.method = :method")

public class TestMethodCallsMethod implements Serializable, CodeEntity {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TestMethodCallsMethodPK id;

	//bi-directional many-to-one association TestMethod
    @ManyToOne
    @JoinColumn(name="test_method_id", insertable=false, updatable=false)
	private TestMethod testMethod;
	
	//bi-directional many-to-one association to Method
    @ManyToOne
    @JoinColumn(name="method_id", insertable=false, updatable=false)
	private Method method;
	
	@OneToOne(fetch=FetchType.LAZY)
	private Position position;

    public TestMethodCallsMethod() {
    }

	public TestMethodCallsMethodPK getId() {
		return this.id;
	}

	public void setId(TestMethodCallsMethodPK id) {
		this.id = id;
	}	
	
	public TestMethod getTestMethod() {
		return testMethod;
	}

	public void setTestMethod(TestMethod testMethod) {
		this.testMethod = testMethod;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public Position getPosition() {
		return this.position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

}