package ca.ucalgary.cpsc.ase.FactManager.service;

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

@Stateless(name="ArgumentService", mappedName="ejb/ArgumentService")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ArgumentService extends AbstractService<Argument> implements ArgumentServiceLocal {

	public ArgumentService() {
		super(Argument.class);
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)	
	public List<Argument> create(Method method, List<Clazz> arguments) {
		List<Argument> args = new ArrayList<Argument>();
		for (Clazz clazz : arguments) {
			Argument arg = new Argument();
			arg.setClazz(clazz);
			arg.setMethod(method);
			create(arg);
			args.add(arg);
		}
		return args;
	}

}
