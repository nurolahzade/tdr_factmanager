package ca.ucalgary.cpsc.ase.FactManager.service;

import java.util.List;
import java.util.Set;

import javax.ejb.Local;

import ca.ucalgary.cpsc.ase.common.entity.Clazz;
import ca.ucalgary.cpsc.ase.common.entity.Method;

@Local
public interface MethodServiceLocal extends LocalEJB<Method> {

	public Method create(String name, Clazz clazz, Clazz returnType,
			boolean isConstructor, List<Clazz> arguments, int hash);

	public Method createOrGet(String name, Clazz clazz, Clazz returnType,
			boolean isConstructor, List<Clazz> arguments, int hash);

	public List<Method> find(String name, String fqn, List<String> arguments);

	public List<Method> getMatchingInvocations(Integer id, Set<Integer> methods);

}