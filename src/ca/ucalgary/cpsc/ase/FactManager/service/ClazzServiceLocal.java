package ca.ucalgary.cpsc.ase.FactManager.service;

import java.util.List;
import java.util.Set;

import javax.ejb.Local;

import ca.ucalgary.cpsc.ase.common.entity.Clazz;
import ca.ucalgary.cpsc.ase.common.entity.ObjectType;
import ca.ucalgary.cpsc.ase.common.entity.SourceFile;

@Local
public interface ClazzServiceLocal extends LocalEJB<Clazz> {

	public Clazz create(String className, String packageName, String fqn,
			SourceFile source, ObjectType type);

	public Clazz createOrGet(String className, String packageName, String fqn,
			SourceFile source, ObjectType type);

	public Clazz find(String fqn);

	public List matchReferences(Set<String> fqns);

	public List matchInvocations(Set<Integer> methods);

	public List matchAssertions(Set<Integer> assertions);

	public Long getInvocationsCount(Clazz c);

}