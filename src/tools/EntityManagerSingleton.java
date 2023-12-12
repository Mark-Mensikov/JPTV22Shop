/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Melnikov
 */
public class EntityManagerSingleton {
    private static final EntityManagerSingleton instance = new EntityManagerSingleton();
    private final EntityManagerFactory entityManagerFactory;
    private final EntityManager entityManager;

    private EntityManagerSingleton() {
    // Создание EntityManagerFactory (обычно, она создается один раз при запуске приложения)
        this.entityManagerFactory = Persistence.createEntityManagerFactory("JKTV22ShoppPU");

        // Создание EntityManager
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    public static EntityManagerSingleton getInstance() {
        return instance;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void closeEntityManagerFactory() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}