package name.drserge.library.persistence.repository;

import name.drserge.library.persistence.model.Author;


public interface AuthorRepository extends Repository<Author> {

    Author findOne(Long authorId);

}
