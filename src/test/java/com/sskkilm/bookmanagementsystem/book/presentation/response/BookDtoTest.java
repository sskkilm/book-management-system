package com.sskkilm.bookmanagementsystem.book.presentation.response;

import com.sskkilm.bookmanagementsystem.author.domain.Author;
import com.sskkilm.bookmanagementsystem.book.domain.Book;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookDtoTest {

    @Test
    void from_Book으로_BookDto를_생성할_수_있다() {
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
        BookDto bookDto = BookDto.from(book);

        //then
        assertEquals(1L, bookDto.id());
        assertEquals("오브젝트", bookDto.title());
        assertEquals("1234567890", bookDto.isbn());
        assertEquals(LocalDate.of(2025, 2, 25), bookDto.publicationDate());
    }

}