package name.drserge.library.persistence.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.function.Supplier;


public class GenericJpaRepository<T> implements Repository<T> {

    private EntityManagerFactory entityManagerFactory;

    public GenericJpaRepository(String persistenceUnitName) {
        entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
    }

    @Override
    public T findOne(final Object entityId, final Class<T> aClass) {
        return executeJpaOperation(new JpaOperation<T>(entityManagerFactory) {
            @Override
            public T get() {
                return getEntityManager().find(aClass, entityId);
            }
        }, false);
    }

    @Override
    public List<T> findAll(final Class<T> aClass) {
        return executeJpaOperation(new JpaOperation<List<T>>(entityManagerFactory) {
            @Override
            public List<T> get() {
                return getEntityManager().createQuery("select t from"  + aClass.getSimpleName() +  " t", aClass).getResultList();
            }
        }, false);
    }

    @Override
    public T save(final T entity) {
        return executeJpaOperation(new JpaOperation<T>(entityManagerFactory) {
            @Override
            public T get() {
                return getEntityManager().merge(entity);
            }
        }, true);
    }

    @Override
    public void delete(final T entity) {
        executeJpaOperation(new JpaOperation<Void>(entityManagerFactory) {
            @Override
            public Void get() {
                EntityManager entityManager = this.getEntityManager();
                entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
                return null;
            }
        }, true);
    }

    protected <R> R executeJpaOperation(final JpaOperation<R> operation, final boolean transactional) {
        EntityManager entityManager = operation.getEntityManager();
        try {
            R result;
            if (transactional) {
                result = executeOperationInTransaction(operation, entityManager);
            } else {
                result = operation.get();
            }
            return result;
        } finally {
            operation.getEntityManager().close();
        }
    }

    private <R> R executeOperationInTransaction(final JpaOperation<R> operation, final EntityManager entityManager) {
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        R result = operation.get();
        transaction.commit();

        return result;
     }

    abstract class JpaOperation<R> implements Supplier<R> {
        private final EntityManager entityManager;

        public JpaOperation(final EntityManagerFactory entityManagerFactory) {
            this.entityManager = entityManagerFactory.createEntityManager();
        }

        public EntityManager getEntityManager() {
            return entityManager;
        }
    }

}
