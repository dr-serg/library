package name.drserge.library.persistence.repository;

import junit.framework.TestCase;
import name.drserge.library.persistence.model.Author;
import org.junit.Before;


public class AuthorRepositoryImplTest extends TestCase {

    private AuthorRepository testAuthorRepository = new AuthorRepositoryImpl("library-hasql-persistence-unit");

    @Before
    public void setup() {

    }

    public void shouldFindOneAuthor() {
        // when
        Author author = testAuthorRepository.findOne("1", Author.class);

        //then
        assertEquals("Author name", "John", author.getFirstName());
    }


}