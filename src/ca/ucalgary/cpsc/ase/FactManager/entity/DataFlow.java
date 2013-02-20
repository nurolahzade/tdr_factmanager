package ca.ucalgary.cpsc.ase.FactManager.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the DataFlow database table.
 * 
 */
@Entity
@Table(name="DataFlow")
public class DataFlow implements Serializable, CodeEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	//bi-directional many-to-one association to MethodInvocation
    @ManyToOne
	@JoinColumn(name="from_method_invocation_id")
	private MethodInvocation fromMethod;

	//bi-directional many-to-one association to MethodInvocation
    @ManyToOne
	@JoinColumn(name="to_method_invocation_id")
	private MethodInvocation toMethod;

    public DataFlow() {
    }

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public MethodInvocation getFromMethod() {
		return this.fromMethod;
	}

	public void setFromMethod(MethodInvocation fromMethod) {
		this.fromMethod = fromMethod;
	}
	
	public MethodInvocation getToMethod() {
		return this.toMethod;
	}

	public void setToMethod(MethodInvocation toMethod) {
		this.toMethod = toMethod;
	}	
	
}