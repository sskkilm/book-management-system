package com.sskkilm.bookmanagementsystem.author.presentation.response;

import com.sskkilm.bookmanagementsystem.author.domain.Author;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthorDetailTest {

    @Test
    void from_Author로_AuthorDetail를_생성한다() {
        //given
        Author author = Author.builder()
                .id(1L)
                .name("홍길동")
                .email("domain@example.com")
                .build();

        //when
        AuthorDetail detail = AuthorDetail.from(author);

        //then
        assertEquals(1L, detail.id());
        assertEquals("홍길동", detail.name());
        assertEquals("domain@example.com", detail.email());
    }
}