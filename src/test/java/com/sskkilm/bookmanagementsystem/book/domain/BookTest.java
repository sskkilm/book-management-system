package com.sskkilm.bookmanagementsystem.book.domain;

import com.sskkilm.bookmanagementsystem.author.domain.Author;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    void create_Book을_생성한다() {
        //given
        BookCreate command = new BookCreate(
                "오브젝트", "코드로 이해하는 객체지향 설계", "1234567890",
                LocalDate.of(2025, 2, 25), 1L
        );
        Author author = Author.builder()
                .id(1L)
                .name("홍길동")
                .email("domain@example.com")
                .build();

        //when
        Book book = Book.create(command, author);

        //then
        assertEquals("오브젝트", book.getTitle());
        assertEquals("코드로 이해하는 객체지향 설계", book.getDescription());
        assertEquals("1234567890", book.getIsbn());
        assertEquals(LocalDate.of(2025, 2, 25), book.getPublicationDate());
        assertEquals(1L, book.getAuthor().getId());
        assertEquals("홍길동", book.getAuthor().getName());
        assertEquals("domain@example.com", book.getAuthor().getEmail());
    }

    @Test
    void hasNoChanges_변경사항이_없으면_true를_반환한다() {
        //given
        Author author = Author.builder()
                .id(1L)
                .name("홍길동")
                .email("domain@example.com")
                .build();
        Book book = Book.builder()
                .id(1L)
                .title("오브젝트")
                .description("코드로 이해하는 객체지향 설계")
                .isbn("1234567890")
                .publicationDate(LocalDate.of(2025, 2, 25))
                .author(author)
                .build();

        BookUpdate command = new BookUpdate(1L, "오브젝트", "코드로 이해하는 객체지향 설계",
                "1234567890", LocalDate.of(2025, 2, 25)
        );

        //when
        boolean hasNoChanges = book.hasNoChanges(command);

        //then
        assertTrue(hasNoChanges);
    }

    @Test
    void hasNoChanges_변경사항이_있으면_false를_반환한다() {
        //given
        Author author = Author.builder()
                .id(1L)
                .name("홍길동")
                .email("domain@example.com")
                .build();
        Book book = Book.builder()
                .id(1L)
                .title("오브젝트")
                .description("코드로 이해하는 객체지향 설계")
                .isbn("1234567890")
                .publicationDate(LocalDate.of(2025, 2, 25))
                .author(author)
                .build();

        BookUpdate command = new BookUpdate(1L, "오브젝트2", "코드로 이해하는 객체지향 설계",
                "1234567890", LocalDate.of(2025, 2, 25)
        );

        //when
        boolean hasNoChanges = book.hasNoChanges(command);

        //then
        assertFalse(hasNoChanges);
    }

    @Test
    void hasIsbnChange_isbn_변경사항이_있으면_true를_반환한다() {
        //given
        Author author = Author.builder()
                .id(1L)
                .name("홍길동")
                .email("domain@example.com")
                .build();
        Book book = Book.builder()
                .id(1L)
                .title("오브젝트")
                .description("코드로 이해하는 객체지향 설계")
                .isbn("1234567890")
                .publicationDate(LocalDate.of(2025, 2, 25))
                .author(author)
                .build();

        BookUpdate command = new BookUpdate(1L, "오브젝트", "코드로 이해하는 객체지향 설계",
                "2234567890", LocalDate.of(2025, 2, 25)
        );

        //when
        boolean hasNoChanges = book.hasIsbnChange(command);

        //then
        assertTrue(hasNoChanges);
    }

    @Test
    void hasIsbnChange_isbn_변경사항이_없으면_false를_반환한다() {
        //given
        Author author = Author.builder()
                .id(1L)
                .name("홍길동")
                .email("domain@example.com")
                .build();
        Book book = Book.builder()
                .id(1L)
                .title("오브젝트")
                .description("코드로 이해하는 객체지향 설계")
                .isbn("1234567890")
                .publicationDate(LocalDate.of(2025, 2, 25))
                .author(author)
                .build();

        BookUpdate command = new BookUpdate(1L, "오브젝트", "코드로 이해하는 객체지향 설계",
                "1234567890", LocalDate.of(2025, 2, 25)
        );

        //when
        boolean hasNoChanges = book.hasIsbnChange(command);

        //then
        assertFalse(hasNoChanges);
    }

    @Test
    void update_Book의_정보를_업데이트한다() {
        //given
        Author author = Author.builder()
                .id(1L)
                .name("홍길동")
                .email("domain@example.com")
                .build();
        Book book = Book.builder()
                .id(1L)
                .title("오브젝트")
                .description("코드로 이해하는 객체지향 설계")
                .isbn("1234567890")
                .publicationDate(LocalDate.of(2025, 2, 25))
                .author(author)
                .build();

        BookUpdate command = new BookUpdate(1L, "오브젝트2", "코드로 이해하는 객체지향 설계",
                "2234567890", LocalDate.of(2025, 2, 25)
        );

        //when
        book.update(command);

        //then
        assertEquals(1L, book.getId());
        assertEquals("오브젝트2", book.getTitle());
        assertEquals("코드로 이해하는 객체지향 설계", book.getDescription());
        assertEquals("2234567890", book.getIsbn());
        assertEquals(LocalDate.of(2025, 2, 25), book.getPublicationDate());
    }
}