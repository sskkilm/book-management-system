package com.sskkilm.bookmanagementsystem.author.presentation.response;

import com.sskkilm.bookmanagementsystem.author.domain.Author;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthorCreateResponseTest {

    @Test
    void from_Author로_AuthorCreateResponse를_생성한다() {
        //given
        Author author = Author.builder()
                .id(1L)
                .name("홍길동")
                .email("domain@example.com")
                .build();

        //when
        AuthorCreateResponse response = AuthorCreateResponse.from(author);

        //then
        assertEquals(1L, response.id());
        assertEquals("홍길동", response.name());
        assertEquals("domain@example.com", response.email());
    }
}