package name.drserge.library.persistence.repository;

import java.util.List;


public interface Repository<T> {

    T findOne(final Object entityId, final Class<T> tClass);

    List<T> findAll(final Class<T> aClass);

    T save(final T entity);

    void delete(final T entity);
}
