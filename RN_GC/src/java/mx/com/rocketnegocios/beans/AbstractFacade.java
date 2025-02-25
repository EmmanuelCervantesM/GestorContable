/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.rocketnegocios.beans;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.ejb.EJBTransactionRolledbackException;
import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 *
 * @author Developer1
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        javax.validation.Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(entity);
        if (constraintViolations.size() > 0) {
            System.out.println("Constraint Violations ocurred...");
            for (ConstraintViolation<T> contraints : constraintViolations) {
                System.out.println("ERROR: " +  contraints.getRootBeanClass().getSimpleName() + "." + contraints.getPropertyPath() + " " + contraints.getMessage());
            }
            getEntityManager().persist(entity);
        }
    }

    public void edit(T entity) {
        //getEntityManager().merge(entity);
        try {
            getEntityManager().merge(entity);
        } catch (EJBTransactionRolledbackException e) {
            Throwable t = e.getCause();
            while ((t != null) && !(t instanceof ConstraintViolationException)) {
                t = t.getCause();
            }
            if (t instanceof ConstraintViolationException) {
                // Here you're sure you have a ConstraintViolationException, you can handle it
                System.out.println("Sucedio ConstraintViolationException");
                for (ConstraintViolation actual : ((ConstraintViolationException) t).getConstraintViolations()) {
                    System.out.println("1 ACTUAL VIOLATION:" + actual.toString());
                }
            }
        } catch (ConstraintViolationException e) {
            // Aqui tira los errores de constraint
            for (ConstraintViolation actual : e.getConstraintViolations()) {
                System.out.println("2 ACTUAL VIOLATION: " + actual.toString() + " | " + e.getMessage());
            }
        }
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
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
        q.setMaxResults(range[1] - range[0] + 1);
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
    
       public void crea(T entity) {
        System.out.println("AQUI ESTOY");
        //getEntityManager().merge(entity);
        try {
            getEntityManager().merge(entity);
        } catch (EJBTransactionRolledbackException e) {
            Throwable t = e.getCause();
            while ((t != null) && !(t instanceof ConstraintViolationException)) {
                t = t.getCause();
            }
            if (t instanceof ConstraintViolationException) {
                // Here you're sure you have a ConstraintViolationException, you can handle it
                System.out.println("Sucedio ConstraintViolationException");
                for (ConstraintViolation actual : ((ConstraintViolationException) t).getConstraintViolations()) {
                    System.out.println("1 ACTUAL VIOLATION:"+actual.toString());
                }
            }
        } catch (ConstraintViolationException e) {
            // Aqui tira los errores de constraint
            for (ConstraintViolation actual : e.getConstraintViolations()) {
                System.out.println("2 ACTUAL VIOLATION:"+actual.toString());
            }
        }
    }


}
