package ca.ucalgary.cpsc.ase.FactManager.service;

import javax.ejb.Local;

import ca.ucalgary.cpsc.ase.common.entity.Assertion;
import ca.ucalgary.cpsc.ase.common.entity.Method;
import ca.ucalgary.cpsc.ase.common.entity.MethodInvocation;
import ca.ucalgary.cpsc.ase.common.entity.Position;
import ca.ucalgary.cpsc.ase.common.entity.TestMethod;

@Local
public interface MethodInvocationServiceLocal extends LocalEJB<MethodInvocation> {

	public MethodInvocation create(TestMethod tm, Method m, Assertion a,
			Position p);

	public void addDataFlowRelationship(MethodInvocation from, MethodInvocation to);
}