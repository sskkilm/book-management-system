package com.sskkilm.bookmanagementsystem.book.presentation.request;

import com.sskkilm.bookmanagementsystem.book.domain.BookCreate;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookCreateRequestTest {

    @Test
    void toCommand_BookCreateRequest로_BookCreate를_생성한다() {
        //given
        BookCreateRequest request = new BookCreateRequest(
                "오브젝트", "코드로 이해하는 객체지향 설계", "1234567890",
                LocalDate.of(2025, 2, 25), 1L
        );

        //when
        BookCreate command = request.toCommand();

        //then
        assertEquals("오브젝트", command.title());
        assertEquals("코드로 이해하는 객체지향 설계", command.description());
        assertEquals("1234567890", command.isbn());
        assertEquals(LocalDate.of(2025, 2, 25), command.publicationDate());
        assertEquals(1L, command.authorId());
    }
}