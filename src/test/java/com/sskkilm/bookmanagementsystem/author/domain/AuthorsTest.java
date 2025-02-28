package com.sskkilm.bookmanagementsystem.author.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthorsTest {

    @Test
    void of_Author_List로_Authors를_생성한다() {
        //given
        List<Author> authorList = List.of(
                Author.builder()
                        .id(1L)
                        .name("홍길동")
                        .email("domain@example.com")
                        .build(),
                Author.builder()
                        .id(2L)
                        .name("철수")
                        .email("domain2@example.com")
                        .build()
        );

        //when
        Authors result = Authors.of(authorList);

        //then
        assertEquals(2, result.authors().size());

        Author author1 = result.authors().get(0);
        assertEquals(1L, author1.getId());
        assertEquals("홍길동", author1.getName());
        assertEquals("domain@example.com", author1.getEmail());

        Author author2 = result.authors().get(1);
        assertEquals(2L, author2.getId());
        assertEquals("철수", author2.getName());
        assertEquals("domain2@example.com", author2.getEmail());
    }
}