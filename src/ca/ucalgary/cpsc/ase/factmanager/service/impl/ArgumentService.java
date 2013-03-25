package ca.ucalgary.cpsc.ase.factmanager.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import ca.ucalgary.cpsc.ase.common.entity.Argument;
import ca.ucalgary.cpsc.ase.common.entity.Clazz;
import ca.ucalgary.cpsc.ase.common.entity.Method;
import ca.ucalgary.cpsc.ase.common.service.ServiceDirectory;
import ca.ucalgary.cpsc.ase.factmanager.service.AbstractService;
import ca.ucalgary.cpsc.ase.factmanager.service.ArgumentServiceLocal;

@Stateless(name="ArgumentService")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ArgumentService extends AbstractService<Argument> implements ArgumentServiceLocal {

	public ArgumentService() {
		super(Argument.class);
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)	
	public List<Argument> create(Method method, List<Clazz> arguments) {
		List<Argument> args = new ArrayList<Argument>();
		for (int i = 0; i < arguments.size(); ++i) {
			Argument arg = new Argument();
			arg.setClazz(arguments.get(i));
			arg.setMethod(method);
			arg.setOrder(i);
			create(arg);
			args.add(arg);
		}
		return args;
	}

}
