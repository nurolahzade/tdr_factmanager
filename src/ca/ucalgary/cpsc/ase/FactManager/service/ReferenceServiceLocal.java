package ca.ucalgary.cpsc.ase.FactManager.service;

import java.util.List;
import java.util.Set;

import javax.ejb.Local;

import ca.ucalgary.cpsc.ase.common.entity.Clazz;
import ca.ucalgary.cpsc.ase.common.entity.Position;
import ca.ucalgary.cpsc.ase.common.entity.Reference;
import ca.ucalgary.cpsc.ase.common.entity.TestMethod;

@Local
public interface ReferenceServiceLocal extends LocalEJB<Reference> {

	public Reference create(String name, Clazz clazz, Clazz declaringClazz,
			TestMethod testMethod, Position position);

	public Reference createOrGet(String name, Clazz clazz,
			Clazz declaringClazz, TestMethod testMethod, Position position);

	public Reference find(String name, Clazz clazz, Clazz declaringClazz,
			TestMethod testMethod);

	public List<Reference> getMatchingReferences(Integer id, Set<String> fqns);

}