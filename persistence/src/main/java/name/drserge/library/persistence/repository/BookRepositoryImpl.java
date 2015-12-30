package name.drserge.library.persistence.repository;

import name.drserge.library.persistence.model.Book;


public class BookRepositoryImpl extends GenericJpaRepository<Book> implements BookRepository {


    public BookRepositoryImpl(String persistenceUnitName) {
        super(persistenceUnitName);
    }

    @Override
    public Book findOne(Long bookId) {
        return super.findOne(bookId, Book.class);
    }
}
