package ca.ucalgary.cpsc.ase.FactManager.service;

import java.util.ArrayList;
import java.util.List;

import ca.ucalgary.cpsc.ase.FactManager.entity.Argument;
import ca.ucalgary.cpsc.ase.FactManager.entity.Clazz;
import ca.ucalgary.cpsc.ase.FactManager.entity.Method;

public class ArgumentService extends AbstractService<Argument> {

	public ArgumentService() {
		super(Argument.class);
	}
	
	public List<Argument> create(Method method, List<Clazz> arguments) {
		List<Argument> args = new ArrayList<Argument>();
		beginTransaction();
		for (Clazz clazz : arguments) {
			Argument arg = new Argument();
			arg.setClazz(clazz);
			arg.setMethod(method);
			create(arg);
			args.add(arg);
		}
		commitTransaction();
		return args;
	}

}
