package name.drserge.library.persistence.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.function.Supplier;


public class GenericJpaRepository<T> implements Repository<T> {

    private EntityManagerFactory entityManagerFactory;

    public GenericJpaRepository(String persistenceUnitName) {
        entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
    }

    @Override
    public T findOne(Object entityId, Class<T> aClass) {
        return executeJpaOperation(new JpaOperation<T>(entityManagerFactory) {
            @Override
            public T get() {
                return getEntityManager().find(aClass, entityId);
            }
        });
    }

    @Override
    public List<T> findAll(Class<T> aClass) {
        return executeJpaOperation(new JpaOperation<List<T>>(entityManagerFactory) {
            @Override
            public List<T> get() {
                return getEntityManager().createQuery("select t from"  + aClass.getSimpleName() +  " t", aClass).getResultList();
            }
        });
    }

    @Override
    public T save(T entity) {
        return null;
    }

    @Override
    public void delete(Object entityId) {

    }

    protected <R> R executeJpaOperation(final JpaOperation<R> operation) {
        operation.getEntityManager();
        try {
            return operation.get();
        } finally {
            operation.getEntityManager().close();
        }
    }

    abstract class JpaOperation<R> implements Supplier<R> {
        private final EntityManager entityManager;

        public JpaOperation(EntityManagerFactory entityManagerFactory) {
            this.entityManager = entityManagerFactory.createEntityManager();
        }

        public EntityManager getEntityManager() {
            return entityManager;
        }
    }

}
