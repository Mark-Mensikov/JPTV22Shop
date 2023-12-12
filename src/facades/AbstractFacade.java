/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author pupil
 */

    public abstract class AbstractFacade<T> {
    
    private final Class<T> entityClass;
    
    public AbstractFacade(Class<T> entityClass){
        this.entityClass = entityClass;
    }
    
    protected abstract EntityManager getEntityManager();
    
    public void create(T entity){
        try {
           getEntityManager().getTransaction().begin();
              getEntityManager().persist(entity);
           getEntityManager().getTransaction().commit();
            
        } catch (Exception e) {
            if(getEntityManager().getTransaction() != null && getEntityManager().getTransaction().isActive()){
                getEntityManager().getTransaction().rollback();
            }
        }
    }
    public T find(Long id){
        return getEntityManager().find(entityClass, id);
    }
    
    public List<T> findAll(){
        CriteriaQuery criteriaQuery = getEntityManager().getCriteriaBuilder().createQuery();
        criteriaQuery.select(criteriaQuery.from(entityClass));
        return getEntityManager().createQuery(criteriaQuery).getResultList();
    }
    
    public void edit(T entity){
         try {
           getEntityManager().getTransaction().begin();
              getEntityManager().merge(entity);
           getEntityManager().getTransaction().commit();
            
        } catch (Exception e) {
            if(getEntityManager().getTransaction() != null && getEntityManager().getTransaction().isActive()){
                getEntityManager().getTransaction().rollback();
            }
        }
    }
}
