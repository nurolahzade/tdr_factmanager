package ca.ucalgary.cpsc.ase.FactManager.entity;

import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the SourceFile database table.
 * 
 */
@Entity
public class SourceFile implements CodeEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	//bi-directional many-to-one association to Class
	@OneToMany(mappedBy="sourceFile")
	private Set<Clazz> clazzs;

	//bi-directional many-to-one association to Project
    @ManyToOne
	private Project project;
    
    
    @Column(name="path")
	private String path;

    public SourceFile() {
    }

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Set<Clazz> getClazzs() {
		return this.clazzs;
	}

	public void setClazzs(Set<Clazz> clazzs) {
		this.clazzs = clazzs;
	}
	
	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
		
}