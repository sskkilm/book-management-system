package com.sskkilm.bookmanagementsystem.author.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorTest {

    @Test
    void create_Author를_생성한다() {
        // given
        AuthorCreate command = new AuthorCreate("홍길동", "domain@example.com");

        // when
        Author author = Author.create(command);

        // then
        assertEquals("홍길동", author.getName());
        assertEquals("domain@example.com", author.getEmail());
    }

    @Test
    void hasNoChanges_변경사항이_없으면_true를_반환한다() {
        // given
        Author author = Author.builder()
                .id(1L)
                .name("홍길동")
                .email("domain@example.com")
                .build();

        AuthorUpdate command = new AuthorUpdate(1L, "홍길동", "domain@example.com");

        // when
        boolean hasNoChanges = author.hasNoChanges(command);

        // then
        assertTrue(hasNoChanges);
    }

    @Test
    void hasNoChanges_변경사항이_있으면_false를_반환한다() {
        // given
        Author author = Author.builder()
                .id(1L)
                .name("홍길동")
                .email("domain@example.com")
                .build();

        AuthorUpdate command = new AuthorUpdate(1L, "홍길동2", "domain2@example.com");

        // when
        boolean hasNoChanges = author.hasNoChanges(command);

        // then
        assertFalse(hasNoChanges);
    }

    @Test
    void hasEmailChange_이메일_변경사항이_있으면_true를_반환한다() {
        // given
        Author author = Author.builder()
                .id(1L)
                .name("홍길동")
                .email("domain@example.com")
                .build();

        AuthorUpdate command = new AuthorUpdate(1L, "홍길동", "domain2@example.com");

        // when
        boolean hasEmailChange = author.hasEmailChange(command);

        // then
        assertTrue(hasEmailChange);
    }

    @Test
    void hasEmailChange_이메일_변경사항이_없으면_false를_반환한다() {
        // given
        Author author = Author.builder()
                .id(1L)
                .name("홍길동")
                .email("domain@example.com")
                .build();

        AuthorUpdate command = new AuthorUpdate(1L, "홍길동", "domain@example.com");

        // when
        boolean hasEmailChange = author.hasEmailChange(command);

        // then
        assertFalse(hasEmailChange);
    }

    @Test
    void update_Author의_정보를_업데이트한다() {
        // given
        Author author = Author.builder()
                .id(1L)
                .name("홍길동")
                .email("domain@example.com")
                .build();

        AuthorUpdate command = new AuthorUpdate(1L, "철수", "newDomain@example.com");

        // when
        author.update(command);

        // then
        assertEquals("철수", author.getName());
        assertEquals("newDomain@example.com", author.getEmail());
    }
}