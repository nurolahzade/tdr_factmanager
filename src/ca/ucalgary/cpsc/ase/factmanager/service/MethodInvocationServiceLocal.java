package ca.ucalgary.cpsc.ase.factmanager.service;

import java.util.List;
import java.util.Set;

import javax.ejb.Local;

import ca.ucalgary.cpsc.ase.common.entity.Assertion;
import ca.ucalgary.cpsc.ase.common.entity.Method;
import ca.ucalgary.cpsc.ase.common.entity.MethodInvocation;
import ca.ucalgary.cpsc.ase.common.entity.Position;
import ca.ucalgary.cpsc.ase.common.entity.TestMethod;

@Local
public interface MethodInvocationServiceLocal extends ServiceFacade<MethodInvocation> {

	public MethodInvocation create(TestMethod tm, Method m, Assertion a,
			Position p);

	public void addDataFlowRelationship(MethodInvocation from, MethodInvocation to);

	public List getTestsWithMethodToMethodDataFlowPath(Set<Integer> from, Set<Integer> to);

	public List getTestsWithMethodToAssertionDataFlowPath(
			Set<Integer> from, Assertion to);
}