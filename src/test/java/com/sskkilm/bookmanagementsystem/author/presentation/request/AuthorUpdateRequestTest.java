package com.sskkilm.bookmanagementsystem.author.presentation.request;

import com.sskkilm.bookmanagementsystem.author.domain.AuthorUpdate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthorUpdateRequestTest {

    @Test
    void toCommand_AuthorUpdateRequest로_AuthorUpdate를_생성한다() {
        //given
        AuthorUpdateRequest request = new AuthorUpdateRequest("홍길동", "domain@example.com");

        //when
        AuthorUpdate command = request.toCommand(1L);

        //then
        assertEquals(1L, command.id());
        assertEquals("홍길동", command.name());
        assertEquals("domain@example.com", command.email());
    }
}