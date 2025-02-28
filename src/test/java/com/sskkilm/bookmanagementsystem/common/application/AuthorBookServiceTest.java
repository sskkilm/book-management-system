package com.sskkilm.bookmanagementsystem.common.application;

import com.sskkilm.bookmanagementsystem.author.application.AuthorRepository;
import com.sskkilm.bookmanagementsystem.book.application.BookRepository;
import com.sskkilm.bookmanagementsystem.common.error.ApiException;
import com.sskkilm.bookmanagementsystem.common.error.ErrorCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.sskkilm.bookmanagementsystem.common.error.ErrorCode.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class AuthorBookServiceTest {

    @Mock
    AuthorRepository authorRepository;

    @Mock
    BookRepository bookRepository;

    @InjectMocks
    AuthorBookService authorBookService;

    @Test
    void getAuthorByAuthorId_특정_id의_Author을_찾을_수_없으면_ApiException이_발생한다_AUTHOR_NOT_FOUND() {
        //given
        given(authorRepository.findById(1L))
                .willReturn(Optional.empty());

        //when
        ApiException exception = assertThrows(
                ApiException.class, () -> authorBookService.getAuthorByAuthorId(1L)
        );

        //then
        assertEquals(AUTHOR_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void deleteAllBooksByAuthorId_특정_저자의_도서를_모두_삭제한다() {
        //given
        doNothing().when(bookRepository).deleteAllBooksByAuthorId(1L);

        //when
        authorBookService.deleteAllBooksByAuthorId(1L);

        //then
    }
}