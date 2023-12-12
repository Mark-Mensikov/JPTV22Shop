/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entity.Sale;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import tools.EntityManagerSingleton;

/**
 *
 * @author pupil
 */
public class SaleFacade extends AbstractFacade<Sale>{
    private EntityManager em;

    public SaleFacade(Class<Sale> entityClass) {
        super(entityClass);
    }

    public SaleFacade() {
        super(Sale.class);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPTV22ShopPU");
        this.em = emf.createEntityManager();
        
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}

