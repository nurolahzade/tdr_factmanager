package ca.ucalgary.cpsc.ase.FactManager.service;

import java.util.List;

import javax.ejb.Local;

import ca.ucalgary.cpsc.ase.common.entity.Argument;
import ca.ucalgary.cpsc.ase.common.entity.Clazz;
import ca.ucalgary.cpsc.ase.common.entity.Method;

@Local
public interface ArgumentServiceLocal extends LocalEJB<Argument> {

	public List<Argument> create(Method method, List<Clazz> arguments);

}