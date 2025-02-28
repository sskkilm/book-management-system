package com.sskkilm.bookmanagementsystem.author.presentation.response;

import com.sskkilm.bookmanagementsystem.author.domain.Author;
import com.sskkilm.bookmanagementsystem.author.domain.Authors;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthorsResponseTest {

    @Test
    void from_Authors로_AuthorsResponse를_생성한다() {
        //given
        Authors authors = new Authors(
                List.of(
                        Author.builder()
                                .id(1L)
                                .name("홍길동")
                                .email("domain1@example.com")
                                .build(),
                        Author.builder()
                                .id(2L)
                                .name("철수")
                                .email("domain2@example.com")
                                .build()
                )
        );

        //when
        AuthorsResponse response = AuthorsResponse.from(authors);

        //then
        assertEquals(2, response.authors().size());

        AuthorDto authorDto1 = response.authors().get(0);
        assertEquals(1L, authorDto1.id());
        assertEquals("홍길동", authorDto1.name());

        AuthorDto authorDto2 = response.authors().get(1);
        assertEquals(2L, authorDto2.id());
        assertEquals("철수", authorDto2.name());
    }
}