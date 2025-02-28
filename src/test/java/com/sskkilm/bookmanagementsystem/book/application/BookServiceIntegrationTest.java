package com.sskkilm.bookmanagementsystem.book.application;

import com.sskkilm.bookmanagementsystem.author.application.AuthorRepository;
import com.sskkilm.bookmanagementsystem.author.domain.Author;
import com.sskkilm.bookmanagementsystem.book.domain.Book;
import com.sskkilm.bookmanagementsystem.book.domain.BookCreate;
import com.sskkilm.bookmanagementsystem.book.domain.BookUpdate;
import com.sskkilm.bookmanagementsystem.common.error.ApiException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static com.sskkilm.bookmanagementsystem.common.error.ErrorCode.BOOK_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class BookServiceIntegrationTest {

    @Autowired
    BookService bookService;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Test
    void create_새로운_Book을_생성한다() {
        // Given
        Author author = authorRepository.save(
                Author.builder()
                        .name("홍길동")
                        .email("domain@example.com")
                        .build()
        );
        BookCreate command = new BookCreate(
                "오브젝트",
                "코드로 이해하는 객체지향 설계",
                "1234567890",
                LocalDate.of(2025, 2, 25),
                author.getId()
        );

        // When
        Book book = bookService.create(command);

        // Then
        Book saved = bookRepository.getById(book.getId());

        assertEquals("오브젝트", saved.getTitle());
        assertEquals("코드로 이해하는 객체지향 설계", saved.getDescription());
        assertEquals("1234567890", saved.getIsbn());
        assertEquals(LocalDate.of(2025, 2, 25), saved.getPublicationDate());

        assertEquals("홍길동", saved.getAuthor().getName());
        assertEquals("domain@example.com", saved.getAuthor().getEmail());
    }

    @Test
    void getByIdWithAuthor_특정_id의_Book을_조회한다() {
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
        Book savedBook = bookService.getByIdWithAuthor(book.getId());

        // Then
        assertEquals("오브젝트", savedBook.getTitle());
        assertEquals("코드로 이해하는 객체지향 설계", savedBook.getDescription());
        assertEquals("1234567890", savedBook.getIsbn());
        assertEquals(LocalDate.of(2025, 2, 25), savedBook.getPublicationDate());

        assertEquals("홍길동", savedBook.getAuthor().getName());
        assertEquals("domain@example.com", savedBook.getAuthor().getEmail());
    }

    @Test
    void update_Book의_정보를_업데이트한다() {
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

        BookUpdate command = new BookUpdate(
                book.getId(),
                "오브젝트2",
                "코드로 이해하는 객체지향 설계2",
                "2234567890",
                LocalDate.of(2025, 2, 26)
        );

        // When
        bookService.update(command);

        // Then
        assertEquals("오브젝트2", book.getTitle());
        assertEquals("코드로 이해하는 객체지향 설계2", book.getDescription());
        assertEquals("2234567890", book.getIsbn());
        assertEquals(LocalDate.of(2025, 2, 26), book.getPublicationDate());

        assertEquals("홍길동", book.getAuthor().getName());
        assertEquals("domain@example.com", book.getAuthor().getEmail());
    }

    @Test
    void delete_특정_Book을_삭제한다() {
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
        bookService.deleteById(book.getId());

        // Then
        ApiException exception = assertThrows(
                ApiException.class, () -> bookRepository.getById(book.getId())
        );

        assertEquals(BOOK_NOT_FOUND, exception.getErrorCode());
    }
}