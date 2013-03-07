package ca.ucalgary.cpsc.ase.FactManager.service;

import java.util.List;

public interface LocalEJB<T> {

	public void create(T entity);

	public void update(T entity);

	public void remove(T entity);

	public T find(Object id);

	public List<T> findAll();

	public List<T> findRange(int[] range);

	public int count();

}