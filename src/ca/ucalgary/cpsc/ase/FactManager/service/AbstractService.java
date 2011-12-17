package ca.ucalgary.cpsc.ase.FactManager.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;

public abstract class AbstractService<T> {
	
	private static Logger logger = Logger.getLogger(AbstractService.class);
	
	protected EntityManager em;	
	private Class<T> entityClass;
	
    public AbstractService(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    
    protected synchronized EntityManager getEntityManager() {
    	if (em == null) {
    		EntityManagerFactory factory = Persistence.createEntityManagerFactory("FactManager");
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

	public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void update(T entity) {
        getEntityManager().merge(entity);
    }
    
    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
        getEntityManager().flush();
    }

    public T find(Object id) {
        try {
			return getEntityManager().find(entityClass, id);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			return null;
		}
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
    protected void beginTransaction() {
    	getEntityManager().getTransaction().begin();
    }
    
    protected void commitTransaction() {
    	getEntityManager().getTransaction().commit();
    }
    
    protected void rollbackTransaction() {
    	getEntityManager().getTransaction().rollback();
    }
	
}
