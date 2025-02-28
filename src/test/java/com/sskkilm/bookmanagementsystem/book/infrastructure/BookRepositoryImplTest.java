package com.sskkilm.bookmanagementsystem.book.infrastructure;

import com.sskkilm.bookmanagementsystem.common.error.ApiException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.sskkilm.bookmanagementsystem.common.error.ErrorCode.BOOK_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BookRepositoryImplTest {

    @Mock
    BookJpaRepository bookJpaRepository;

    @Mock
    BookQuerydslRepository bookQuerydslRepository;

    @InjectMocks
    BookRepositoryImpl bookRepository;

    @Test
    void getByIdWithAuthor_특정_id의_Book을_찾을_수_없으면_ApiException이_발생한다_BOOK_NOT_FOUND() {
        //given
        given(bookJpaRepository.findByIdWithAuthor(1L))
                .willReturn(Optional.empty());

        //when
        ApiException exception = assertThrows(
                ApiException.class, () -> bookRepository.getByIdWithAuthor(1L)
        );

        //then
        assertEquals(BOOK_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void getById_특정_id의_Book을_찾을_수_없으면_ApiException이_발생한다_BOOK_NOT_FOUND() {
        //given
        given(bookJpaRepository.findById(1L))
                .willReturn(Optional.empty());

        //when
        ApiException exception = assertThrows(
                ApiException.class, () -> bookRepository.getById(1L)
        );

        //then
        assertEquals(BOOK_NOT_FOUND, exception.getErrorCode());
    }
}