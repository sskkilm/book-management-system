package com.sskkilm.bookmanagementsystem.author.application;

import com.sskkilm.bookmanagementsystem.author.domain.Author;
import com.sskkilm.bookmanagementsystem.author.domain.AuthorCreate;
import com.sskkilm.bookmanagementsystem.author.domain.AuthorUpdate;
import com.sskkilm.bookmanagementsystem.book.application.BookRepository;
import com.sskkilm.bookmanagementsystem.book.domain.Book;
import com.sskkilm.bookmanagementsystem.common.error.ApiException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static com.sskkilm.bookmanagementsystem.common.error.ErrorCode.BOOK_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AuthorIntegrationServiceTest {

    @Autowired
    AuthorService authorService;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BookRepository bookRepository;

    @Test
    void create_새로운_Author을_생성한다() {
        // Given
        AuthorCreate command = new AuthorCreate("홍길동", "domain@example.com");

        // When
        Author author = authorService.create(command);

        // Then
        Author savedAuthor = authorRepository.findById(author.getId()).get();

        assertEquals("홍길동", savedAuthor.getName());
        assertEquals("domain@example.com", savedAuthor.getEmail());
    }

    @Test
    void getById_특정_id의_Author을_조회한다() {
        // Given
        Author author = authorRepository.save(
                Author.builder()
                        .name("홍길동")
                        .email("domain@example.com")
                        .build()
        );

        // When
        Author savedAuthor = authorService.getById(author.getId());

        // Then
        assertEquals("홍길동", savedAuthor.getName());
        assertEquals("domain@example.com", savedAuthor.getEmail());
    }

    @Test
    void update_Author의_정보를_업데이트한다() {
        // Given
        Author author = authorRepository.save(
                Author.builder()
                        .name("홍길동")
                        .email("domain@example.com")
                        .build()
        );

        AuthorUpdate command = new AuthorUpdate(author.getId(), "철수", "domain2@example.com");

        // When
        authorService.update(command);

        // Then
        Author savedAuthor = authorRepository.findById(author.getId()).get();

        assertEquals("철수", savedAuthor.getName());
        assertEquals("domain2@example.com", savedAuthor.getEmail());
    }

    @Test
    void deleteById_특정_Author을_삭제하고_연관_도서도_삭제한다() {
        // Given
        Author author = authorRepository.save(
                Author.builder()
                        .name("홍길동")
                        .email("domain@example.com")
                        .build()
        );
        Book book = bookRepository.save(
                Book.builder()
                        .title("오브젝트")
                        .description("코드로 이해하는 객체지향 설계")
                        .isbn("1234567890")
                        .publicationDate(LocalDate.of(2025, 2, 25))
                        .author(author)
                        .build()
        );

        // When
        authorService.deleteById(author.getId());

        // Then
        Optional<Author> optionalAuthor = authorRepository.findById(author.getId());
        ApiException exception = assertThrows(
                ApiException.class, () -> bookRepository.getById(book.getId())
        );

        assertTrue(optionalAuthor.isEmpty());
        assertEquals(BOOK_NOT_FOUND, exception.getErrorCode());
    }
}