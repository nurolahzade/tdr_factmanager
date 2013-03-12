package ca.ucalgary.cpsc.ase.FactManager.service;

import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.UserTransaction;

import ca.ucalgary.cpsc.ase.FactManager.service.ProjectServiceLocal;
import ca.ucalgary.cpsc.ase.FactManager.service.RepositoryFileServiceLocal;
import ca.ucalgary.cpsc.ase.common.service.MyTestServiceRemote;

/**
 * Session Bean implementation class MyTestService
 */
@Stateful(name="MyTestService", mappedName="ejb/MyTestService")
@TransactionManagement(TransactionManagementType.BEAN)
public class MyTestService implements MyTestServiceRemote, MyTestServiceLocal {
	
	@EJB(name = "ProjectService")
	ProjectServiceLocal projectService;
	@EJB(name = "RepositoryFileService")
	RepositoryFileServiceLocal repositoryFileService;

	@Resource
	private UserTransaction tx;

	public MyTestService() {
    }
    
    @Override
    public String getCurrentTime() {
    	return new Date().toString();
    }
    
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void doSomeDbTask() {
    	try {
//			tx.begin();
			
	    	projectService.create("TEST", "123");
	    	repositoryFileService.create("/dummy");
			
//	    	tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @Override
	public void startTransaction() {
    	try {
        	tx.begin();    		
    	} catch (Exception e) {
    		throw new EJBException(e);
    	}
    }
    
    @Override
	public void commitTransaction() {
    	try {
        	tx.commit();    		
    	} catch (Exception e) {
    		throw new EJBException(e);
    	}
    }
    
    @Override
	public void rollbackTransaction() {
    	try {
        	tx.rollback();    		
    	} catch (Exception e) {
    		throw new EJBException(e);
    	}
    }

}
