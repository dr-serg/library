package name.drserge.library.persistence.repository;

import name.drserge.library.persistence.model.Author;


public class AuthorRepositoryImpl extends GenericJpaRepository<Author> implements AuthorRepository {

    public AuthorRepositoryImpl(String persistenceUnitName) {
        super(persistenceUnitName);
    }

}
