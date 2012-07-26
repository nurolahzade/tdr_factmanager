package ca.ucalgary.cpsc.ase.FactManager.entity;

import javax.persistence.*;

import java.util.Set;


/**
 * The persistent class for the Project database table.
 * 
 */
@Entity
@Table(name="Project")

public class Project implements CodeEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	//bi-directional many-to-one association to SourceFile
	@OneToMany(mappedBy="project")
	private Set<SourceFile> sourceFiles;

	@Column(name="name")
	String name;
	
	@Column(name="version")
	String version;
	
    public Project() {
    }

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Set<SourceFile> getSourceFiles() {
		return this.sourceFiles;
	}

	public void setSourceFiles(Set<SourceFile> sourceFiles) {
		this.sourceFiles = sourceFiles;
	}		
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return this.getClass().getName() + "[id=" + id + "]";
	}
		
	
}