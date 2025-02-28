package com.sskkilm.bookmanagementsystem.author.presentation.request;

import com.sskkilm.bookmanagementsystem.author.domain.AuthorCreate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorCreateRequestTest {

    @Test
    void toCommand_AuthorCreateRequest로_AuthorCreate를_생성한다() {
        //given
        AuthorCreateRequest request = new AuthorCreateRequest("홍길동", "domain@example.com");

        //when
        AuthorCreate command = request.toCommand();

        //then
        assertEquals("홍길동", command.name());
        assertEquals("domain@example.com", command.email());
    }
}