package ca.ucalgary.cpsc.ase.factmanager.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.UserTransaction;

import ca.ucalgary.cpsc.ase.common.ServiceDirectory;
import ca.ucalgary.cpsc.ase.common.entity.Assertion;
import ca.ucalgary.cpsc.ase.common.entity.AssertionType;
import ca.ucalgary.cpsc.ase.common.entity.Clazz;
import ca.ucalgary.cpsc.ase.common.entity.Method;
import ca.ucalgary.cpsc.ase.common.entity.MethodInvocation;
import ca.ucalgary.cpsc.ase.common.entity.ObjectType;
import ca.ucalgary.cpsc.ase.common.entity.Position;
import ca.ucalgary.cpsc.ase.common.entity.Project;
import ca.ucalgary.cpsc.ase.common.entity.Reference;
import ca.ucalgary.cpsc.ase.common.entity.RepositoryFile;
import ca.ucalgary.cpsc.ase.common.entity.SourceFile;
import ca.ucalgary.cpsc.ase.common.entity.TestMethod;
import ca.ucalgary.cpsc.ase.common.entity.Xception;
import ca.ucalgary.cpsc.ase.common.service.ServiceWrapperRemote;
import ca.ucalgary.cpsc.ase.factmanager.service.AssertionServiceLocal;
import ca.ucalgary.cpsc.ase.factmanager.service.ClazzServiceLocal;
import ca.ucalgary.cpsc.ase.factmanager.service.MethodInvocationServiceLocal;
import ca.ucalgary.cpsc.ase.factmanager.service.MethodServiceLocal;
import ca.ucalgary.cpsc.ase.factmanager.service.PositionServiceLocal;
import ca.ucalgary.cpsc.ase.factmanager.service.ProjectServiceLocal;
import ca.ucalgary.cpsc.ase.factmanager.service.ReferenceServiceLocal;
import ca.ucalgary.cpsc.ase.factmanager.service.RepositoryFileServiceLocal;
import ca.ucalgary.cpsc.ase.factmanager.service.SourceFileServiceLocal;
import ca.ucalgary.cpsc.ase.factmanager.service.TestMethodServiceLocal;
import ca.ucalgary.cpsc.ase.factmanager.service.XceptionServiceLocal;

/**
 * Session Bean implementation class MyTestService
 */
@Stateful(name="ServiceWrapper", mappedName=ServiceDirectory.SERVICE_WRAPPER)
@TransactionManagement(TransactionManagementType.BEAN)
public class ServiceWrapper implements ServiceWrapperRemote {
	
	@EJB(name="AssertionService") AssertionServiceLocal assertionService;
	@EJB(name="ClazzService") ClazzServiceLocal clazzService;
	@EJB(name="MethodInvocationService") MethodInvocationServiceLocal invocationService;
	@EJB(name="MethodService") MethodServiceLocal methodService;
	@EJB(name="PositionService") PositionServiceLocal positionService;	
	@EJB(name="ProjectService") ProjectServiceLocal projectService;
	@EJB(name="ReferenceService") ReferenceServiceLocal referenceService;
	@EJB(name="RepositoryFileService") RepositoryFileServiceLocal repositoryFileService;
	@EJB(name="SourceFileService") SourceFileServiceLocal sourceFileService;
	@EJB(name="TestMethodService") TestMethodServiceLocal testMethodService;
	@EJB(name="XceptionService") XceptionServiceLocal xceptionService;
	
	@Resource
	private UserTransaction tx;

	public ServiceWrapper() {
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

	@Override
	public Assertion createAssertion(AssertionType type) {
		return assertionService.create(type);
	}

	@Override
	public Assertion findAssertion(AssertionType type) {
		return assertionService.find(type);
	}

	@Override
	public Assertion createOrGetAssertion(AssertionType type) {
		return assertionService.createOrGet(type);
	}

	@Override
	public Clazz createClazz(String className, String packageName, String fqn,
			SourceFile source, ObjectType type) {
		return clazzService.create(className, packageName, fqn, source, type);
	}

	@Override
	public Clazz createOrGetClazz(String className, String packageName,
			String fqn, SourceFile source, ObjectType type) {
		return clazzService.createOrGet(className, packageName, fqn, source, type);
	}

	@Override
	public Clazz findClazz(String fqn) {
		return clazzService.find(fqn);
	}

	@Override
	public MethodInvocation createInvocation(TestMethod tm, Method m,
			Assertion a, Position p) {
		return invocationService.create(tm, m, a, p);
	}

	@Override
	public void addDataFlowRelationship(MethodInvocation from,
			MethodInvocation to) {
		invocationService.addDataFlowRelationship(from, to);
	}

	@Override
	public Method createMethod(String name, Clazz clazz, Clazz returnType,
			boolean isConstructor, List<Clazz> arguments, int hash) {
		return methodService.create(name, clazz, returnType, isConstructor, arguments, hash);
	}

	@Override
	public Method createOrGetMethod(String name, Clazz clazz, Clazz returnType,
			boolean isConstructor, List<Clazz> arguments, int hash) {
		return methodService.createOrGet(name, clazz, returnType, isConstructor, arguments, hash);
	}

	@Override
	public List<Method> findMethod(String name, String fqn,
			List<String> arguments) {
		return methodService.find(name, fqn, arguments);
	}

	@Override
	public Position createPosition(Integer start, Integer length) {
		return positionService.create(start, length);
	}

	@Override
	public Project createProject(String name) {
		return projectService.create(name);
	}

	@Override
	public Reference createReference(String name, Clazz clazz,
			Clazz declaringClazz, TestMethod testMethod, Position position) {
		return referenceService.create(name, clazz, declaringClazz, testMethod, position);
	}

	@Override
	public Reference createOrGetReference(String name, Clazz clazz,
			Clazz declaringClazz, TestMethod testMethod, Position position) {
		return referenceService.createOrGet(name, clazz, declaringClazz, testMethod, position);
	}

	@Override
	public Reference findReference(String name, Clazz clazz,
			Clazz declaringClazz, TestMethod testMethod) {
		return referenceService.find(name, clazz, declaringClazz, testMethod);
	}

	@Override
	public RepositoryFile createRepositoryFile(String path) {
		return repositoryFileService.create(path);
	}

	@Override
	public void visitRepositoryFile(RepositoryFile file) {
		repositoryFileService.visit(file);
	}

	@Override
	public void skipRepositoryFile(RepositoryFile file) {
		repositoryFileService.skip(file);
	}

	@Override
	public List<RepositoryFile> findUnvisitedRepositoryFile(int maxResults) {
		return repositoryFileService.findUnvisited(maxResults);
	}

	@Override
	public SourceFile createSourceFile(Project project, String path) {
		return sourceFileService.create(project, path);
	}

	@Override
	public TestMethod createTestMethod(String name, Clazz testClass,
			Position position) {
		return testMethodService.create(name, testClass, position);
	}

	@Override
	public Xception createXception(Clazz clazz, TestMethod method) {
		return xceptionService.create(clazz, method);
	}

	@Override
	public Xception createOrGetXception(Clazz clazz, TestMethod testMethod) {
		return xceptionService.createOrGet(clazz, testMethod);
	}

	@Override
	public Xception findXception(Clazz clazz) {
		return xceptionService.find(clazz);
	}

}
