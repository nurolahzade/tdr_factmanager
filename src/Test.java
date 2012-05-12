import java.util.ArrayList;
import java.util.List;

import ca.ucalgary.cpsc.ase.FactManager.service.TestMethodService;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestMethodService service = new TestMethodService();
		List<String> fqns = new ArrayList<String>();
		fqns.add("java.lang.String");
		fqns.add("int");
		List<Object[]> results = service.matchReferences(fqns);
		for (Object[] item : results) {
			System.out.print(item[0]);
			System.out.print(",");
			System.out.println(item[1]);
		}
	}

}
