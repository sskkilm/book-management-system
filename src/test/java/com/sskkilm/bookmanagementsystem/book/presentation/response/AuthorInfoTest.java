package com.sskkilm.bookmanagementsystem.book.presentation.response;

import com.sskkilm.bookmanagementsystem.author.domain.Author;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthorInfoTest {

    @Test
    void from_Author로_AuthorInfo를_생성한다() {
        //given
        Author author = Author.builder()
                .id(1L)
                .name("홍길동")
                .email("domain@example.com")
                .build();

        //when
        AuthorInfo authorInfo = AuthorInfo.from(author);

        //then
        assertEquals(1L, authorInfo.id());
        assertEquals("홍길동", authorInfo.name());
        assertEquals("domain@example.com", authorInfo.email());
    }
}