package ca.ucalgary.cpsc.ase.FactManager.service;

import javax.ejb.Local;

import ca.ucalgary.cpsc.ase.common.entity.Clazz;
import ca.ucalgary.cpsc.ase.common.entity.TestMethod;
import ca.ucalgary.cpsc.ase.common.entity.Xception;

@Local
public interface XceptionServiceLocal extends LocalEJB<Xception> {

	public Xception create(Clazz clazz, TestMethod method);

	public Xception createOrGet(Clazz clazz, TestMethod testMethod);

	public Xception find(Clazz clazz);

}