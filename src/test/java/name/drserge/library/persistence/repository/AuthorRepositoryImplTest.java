package name.drserge.library.persistence.repository;

import name.drserge.library.persistence.model.Author;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


public class AuthorRepositoryImplTest {

    private AuthorRepository testAuthorRepository = new AuthorRepositoryImpl("library-hasql-persistence-unit");

    @Before
    public void setup() {

    }

    @Test
    public void shouldSaveAndFindOneAuthor() {
        //given
        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Conor");
        author.setBirthday(new Date());

        // when
        testAuthorRepository.save(author);
        Author authorSaved = testAuthorRepository.findOne(1L, Author.class);

        //then
        assertEquals("Author name", "John", authorSaved.getFirstName());
        assertEquals("Author last name", "Conor", authorSaved.getLastName());
    }

    @Test
    public void shouldSaveAndDeleteAuthor() {
        //given
        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Conor");
        author.setBirthday(new Date());

        testAuthorRepository.save(author);
        Author authorSaved = testAuthorRepository.findOne(1L, Author.class);
        assertNotNull(authorSaved);

        // when
        testAuthorRepository.delete(authorSaved);

        // then
        assertNull(testAuthorRepository.findOne(1L, Author.class));
    }


}