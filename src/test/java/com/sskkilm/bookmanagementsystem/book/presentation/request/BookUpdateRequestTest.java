package com.sskkilm.bookmanagementsystem.book.presentation.request;

import com.sskkilm.bookmanagementsystem.book.domain.BookUpdate;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookUpdateRequestTest {

    @Test
    void toCommand_BookUpdateRequest로_BookUpdate를_생성한다() {
        //given
        BookUpdateRequest request = new BookUpdateRequest(
                "오브젝트", "코드로 이해하는 객체지향 설계", "1234567890",
                LocalDate.of(2025, 2, 25)
        );

        //when
        BookUpdate command = request.toCommand(1L);

        //then
        assertEquals(1L, command.id());
        assertEquals("오브젝트", command.title());
        assertEquals("코드로 이해하는 객체지향 설계", command.description());
        assertEquals("1234567890", command.isbn());
        assertEquals(LocalDate.of(2025, 2, 25), command.publicationDate());
    }
}