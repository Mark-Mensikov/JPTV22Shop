/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entity.Product;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import tools.EntityManagerSingleton;

/**
 *
 * @author pupil
 */
public class ProductFacade extends AbstractFacade<Product>{
    private EntityManager em;

    public ProductFacade(Class<Product> entityClass) {
        super(entityClass);
    }

    public ProductFacade() {
        super(Product.class);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPTV22ShopPU");
        this.em = emf.createEntityManager();

        
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
