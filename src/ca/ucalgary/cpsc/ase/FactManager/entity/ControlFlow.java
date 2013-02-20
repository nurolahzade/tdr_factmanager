package ca.ucalgary.cpsc.ase.FactManager.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the ControlFlow database table.
 * 
 */
@Entity
@Table(name="ControlFlow")
public class ControlFlow implements Serializable, CodeEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	//bi-directional one-to-many association to MethodInvocation
    @OneToMany
	private Set<MethodInvocation> invocations;

	//bi-directional many-to-one association to Control
    @ManyToOne
	private Control control;

	//bi-directional many-to-one association to ControlFlow
    @ManyToOne
	@JoinColumn(name="container_control_id")
	private ControlFlow parent;

	//many-to-one association to ControlFlow
	@OneToMany
	private Set<ControlFlow> children;

    public ControlFlow() {
    }

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Set<MethodInvocation> getMethodInvocations() {
		return this.invocations;
	}

	public void setMethodInvocation(Set<MethodInvocation> invocations) {
		this.invocations = invocations;
	}
	
	public Control getControl() {
		return this.control;
	}

	public void setControl(Control control) {
		this.control = control;
	}
	
	public ControlFlow getParent() {
		return this.parent;
	}

	public void setParent(ControlFlow parent) {
		this.parent = parent;
	}
	
	public Set<ControlFlow> getChildren() {
		return this.children;
	}

	public void setChildren(Set<ControlFlow> children) {
		this.children = children;
	}
	
}