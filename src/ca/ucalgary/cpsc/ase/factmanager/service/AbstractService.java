package ca.ucalgary.cpsc.ase.factmanager.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.apache.log4j.Logger;

public abstract class AbstractService<T> implements ServiceFacade<T> {
	
	private static Logger logger = Logger.getLogger(AbstractService.class);
	
	@PersistenceUnit(unitName = "FactManager")
	private EntityManagerFactory factory;
	private EntityManager em;
	private Class<T> entityClass;
	
    public AbstractService(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    
    protected synchronized EntityManager getEntityManager() {
    	if (em == null) {
    		em = factory.createEntityManager();
    	}
    	return em;
    }        

    @Override
	protected void finalize() throws Throwable {
		if (em != null && em.isOpen())
			em.close();
		super.finalize();
	}

	@Override
	public void create(T entity) {
        getEntityManager().persist(entity);
        getEntityManager().flush();
    }

    @Override
	public void update(T entity) {
        getEntityManager().merge(entity);
        getEntityManager().flush();
    }
    
    @Override
	public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
        getEntityManager().flush();
    }

    @Override
	public T find(Object id) {
        try {
			return getEntityManager().find(entityClass, id);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			return null;
		}
    }

    @Override
	public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    @Override
	public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    @Override
	public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
}
