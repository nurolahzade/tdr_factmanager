package ca.ucalgary.cpsc.ase.FactManager.service;

import org.apache.log4j.Logger;

import ca.ucalgary.cpsc.ase.FactManager.entity.Assertion;
import ca.ucalgary.cpsc.ase.FactManager.entity.Method;
import ca.ucalgary.cpsc.ase.FactManager.entity.MethodInvocation;
import ca.ucalgary.cpsc.ase.FactManager.entity.Position;
import ca.ucalgary.cpsc.ase.FactManager.entity.TestMethod;

public class MethodInvocationService extends AbstractService<MethodInvocation> {

	private static Logger logger = Logger.getLogger(MethodInvocation.class);
	
	public MethodInvocationService() {
		super(MethodInvocation.class);
	}

	public MethodInvocation create(TestMethod tm, Method m, Assertion a, Position p) {
		beginTransaction();
		MethodInvocation mi = new MethodInvocation();
		mi.setTestmethod(tm);
		mi.setMethod(m);
		mi.setAssertion(a);
		mi.setPosition(p);
		create(mi);
		commitTransaction();
		return mi;
	}
	
}
