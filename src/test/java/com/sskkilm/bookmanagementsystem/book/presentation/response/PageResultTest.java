package com.sskkilm.bookmanagementsystem.book.presentation.response;

import com.sskkilm.bookmanagementsystem.author.domain.Author;
import com.sskkilm.bookmanagementsystem.book.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PageResultTest {

    @Test
    void from_Page로_PageResult를_생성할_수_있다() {
        //given
        Author author = Author.builder()
                .id(1L)
                .name("홍길동")
                .email("domain@example.com")
                .build();
        List<Book> bookList = List.of(
                Book.builder()
                        .id(1L)
                        .title("오브젝트")
                        .description("코드로 이해하는 객체지향 설계")
                        .isbn("1234567890")
                        .publicationDate(LocalDate.of(2025, 2, 25))
                        .author(author)
                        .build(),
                Book.builder()
                        .id(2L)
                        .title("오브젝트")
                        .description("코드로 이해하는 객체지향 설계")
                        .isbn("2234567890")
                        .publicationDate(LocalDate.of(2025, 2, 25))
                        .author(author)
                        .build()
        );
        PageImpl<Book> books = new PageImpl<>(bookList, PageRequest.of(0, 10), 2);

        //when
        PageResult<BookDto> pageResult = PageResult.from(books);

        //then
        assertEquals(2, pageResult.contents().size());
        assertEquals(1, pageResult.page());
        assertEquals(10, pageResult.size());
        assertEquals(1, pageResult.totalPages());
        assertEquals(2, pageResult.totalElements());
    }
}