package com.sskkilm.bookmanagementsystem.book.presentation.response;

import com.sskkilm.bookmanagementsystem.author.domain.Author;
import com.sskkilm.bookmanagementsystem.book.domain.Book;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookDetailTest {

    @Test
    void from_Book으로_BookDetail을_생성한다() {
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

        //when
        BookDetail bookDetail = BookDetail.from(book);

        //then
        assertEquals(1L, bookDetail.id());
        assertEquals("오브젝트", bookDetail.title());
        assertEquals("코드로 이해하는 객체지향 설계", bookDetail.description());
        assertEquals("1234567890", bookDetail.isbn());
        assertEquals(LocalDate.of(2025, 2, 25), bookDetail.publicationDate());
        assertEquals(1L, bookDetail.author().id());
        assertEquals("홍길동", bookDetail.author().name());
        assertEquals("domain@example.com", bookDetail.author().email());
    }
}