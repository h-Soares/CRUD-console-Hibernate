package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class GenericDAO<T> {
    private static EntityManagerFactory emf; //uma para todas as instâncias
    private EntityManager em;
    private Class<T> entityClass;

    //Bloco static
    static {
        try {
            emf = Persistence.createEntityManagerFactory("example-jpa");
        } catch(Exception e) {
            System.err.println("ERROR: " + e.getMessage());
        }
    }

    public GenericDAO() {
        this(null);
    }

    public GenericDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
        em = emf.createEntityManager();
    }

    //return this para encadear chamadas.
    public GenericDAO<T> beginTransaction() {
        em.getTransaction().begin();
        return this;
    }

    public GenericDAO<T> commitTransaction() {
        em.getTransaction().commit();
        return this;
    }

    public GenericDAO<T> persist(T object) {
        em.persist(object);
        return this;
    }

    public T findById(Integer id) {
        if(entityClass == null)
            throw new UnsupportedOperationException("entityClass can't be null");
        
        return em.find(entityClass, id);
    }

    public GenericDAO<T> update(T object) {
        em.merge(object);
        return this;
    }

    public GenericDAO<T> deleteById(Integer id) {
        try {
            em.remove(findById(id));    
            return this;
        } catch(IllegalArgumentException e) {
            throw new IllegalArgumentException("Id " + id + " doesn't exists");
        }
    }

    public GenericDAO<T> atomicPersist(T object) {
        return beginTransaction().persist(object).commitTransaction();
    }

    public GenericDAO<T> atomicUpdate(T object) {
        return beginTransaction().update(object).commitTransaction();
    }

    public GenericDAO<T> atomicDeleteById(Integer id) {
        return beginTransaction().deleteById(id).commitTransaction();
    }

    public List<T> findAll() {
        if(entityClass == null)
            throw new UnsupportedOperationException("entityClass can't be null");
        
        String jpql = "SELECT e FROM " + entityClass.getName() + " e";
        TypedQuery<T> query = em.createQuery(jpql, entityClass);
        return query.getResultList();
    }

    public List<T> findByInitialLetter(String letter) {
        String jpql = "SELECT e FROM " + entityClass.getName() + " e WHERE SUBSTRING(e.name, 1, 1) = :letter";
        TypedQuery<T> query = em.createQuery(jpql, entityClass);
        query.setParameter("letter", letter);
        return query.getResultList();
    }

    public void closeEntityManager() {
        em.close();
    }

    public static void closeEntityManagerFactory() {
        if(emf != null && emf.isOpen()) {
            emf.close();
        }
        else
            throw new IllegalStateException("Error in closing EntityManagerFactory");
    }
}