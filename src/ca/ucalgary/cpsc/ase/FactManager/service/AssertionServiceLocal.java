package ca.ucalgary.cpsc.ase.FactManager.service;

import java.util.List;
import java.util.Set;

import javax.ejb.Local;

import ca.ucalgary.cpsc.ase.common.entity.Assertion;
import ca.ucalgary.cpsc.ase.common.entity.AssertionType;

@Local
public interface AssertionServiceLocal extends LocalEJB<Assertion> {

	public Assertion create(AssertionType type);

	public Assertion find(AssertionType type);

	public Assertion createOrGet(AssertionType type);

	public List<Assertion> getMatchingAssertions(Integer id,
			Set<Integer> assertions);

}