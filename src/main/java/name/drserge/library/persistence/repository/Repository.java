package name.drserge.library.persistence.repository;

import java.util.List;


public interface Repository<T> {

    T findOne(Object entityId, Class<T> tClass);

    List<T> findAll(Class<T> aClass);

    T save(T entity);

    void delete(Object entityId);
}
