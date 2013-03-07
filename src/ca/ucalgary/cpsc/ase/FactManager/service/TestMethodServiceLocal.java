package ca.ucalgary.cpsc.ase.FactManager.service;

import java.util.List;
import java.util.Set;

import javax.ejb.Local;

import ca.ucalgary.cpsc.ase.common.entity.Clazz;
import ca.ucalgary.cpsc.ase.common.entity.Position;
import ca.ucalgary.cpsc.ase.common.entity.TestMethod;

@Local
public interface TestMethodServiceLocal extends LocalEJB<TestMethod> {

	public TestMethod create(String name, Clazz testClass, Position position);

	public List matchReferences(Set<String> fqns);

	public List matchInvocations(Set<Integer> methods);

	public List matchAssertions(Set<Integer> assertions);

	public List matchAssertionParameters(Set<Integer> methods);

	public Long getInvocationsCount(TestMethod tm);

}