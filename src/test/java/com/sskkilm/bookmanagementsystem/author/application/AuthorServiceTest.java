package com.sskkilm.bookmanagementsystem.author.application;

import com.sskkilm.bookmanagementsystem.author.domain.Author;
import com.sskkilm.bookmanagementsystem.author.domain.AuthorCreate;
import com.sskkilm.bookmanagementsystem.author.domain.AuthorUpdate;
import com.sskkilm.bookmanagementsystem.author.domain.Authors;
import com.sskkilm.bookmanagementsystem.common.application.AuthorBookService;
import com.sskkilm.bookmanagementsystem.common.error.ApiException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.sskkilm.bookmanagementsystem.common.error.ErrorCode.ALREADY_EXIST_EMAIL;
import static com.sskkilm.bookmanagementsystem.common.error.ErrorCode.AUTHOR_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock
    AuthorRepository authorRepository;

    @Mock
    AuthorBookService authorBookService;

    @InjectMocks
    AuthorService authorService;

    @Test
    void create_이미_사용중인_이메일이면_ApiException이_발생한다_ALREADY_EXIST_EMAIL() {
        // given
        AuthorCreate command = new AuthorCreate("홍길동", "domain@example.com");

        given(authorRepository.existsByEmail("domain@example.com"))
                .willReturn(true);

        // when
        ApiException exception = assertThrows(
                ApiException.class, () -> authorService.create(command)
        );

        // then
        assertEquals(ALREADY_EXIST_EMAIL, exception.getErrorCode());
    }

    @Test
    void create_새로운_Author를_생성한다() {
        // given
        AuthorCreate command = new AuthorCreate("홍길동", "domain@example.com");

        given(authorRepository.existsByEmail("domain@example.com"))
                .willReturn(false);

        Author author = Author.builder()
                .id(1L)
                .name("홍길동")
                .email("domain@example.com")
                .build();
        given(authorRepository.save(any(Author.class)))
                .willReturn(author);

        // when
        Author result = authorService.create(command);

        // then
        assertEquals(1L, result.getId());
        assertEquals("홍길동", result.getName());
        assertEquals("domain@example.com", result.getEmail());
    }

    @Test
    void getList_Author_리스트를_조회한다() {
        // given
        List<Author> authors = List.of(
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
        );

        given(authorRepository.findAll())
                .willReturn(authors);

        // when
        Authors result = authorService.getList();

        // then
        assertEquals(2, result.authors().size());

        Author author1 = result.authors().get(0);
        assertEquals(1L, author1.getId());
        assertEquals("홍길동", author1.getName());
        assertEquals("domain1@example.com", author1.getEmail());

        Author author2 = result.authors().get(1);
        assertEquals(2L, author2.getId());
        assertEquals("철수", author2.getName());
        assertEquals("domain2@example.com", author2.getEmail());
    }

    @Test
    void getById_특정_id의_Author를_찾을_수_없으면_ApiException이_발생한다_AUTHOR_NOT_FOUND() {
        // given
        given(authorRepository.findById(1L))
                .willReturn(Optional.empty());

        // when
        ApiException exception = assertThrows(
                ApiException.class, () -> authorService.getById(1L)
        );

        // then
        assertEquals(AUTHOR_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void getById_특정_id의_Author를_조회한다() {
        // given
        given(authorRepository.findById(1L))
                .willReturn(Optional.of(
                        Author.builder()
                                .id(1L)
                                .name("홍길동")
                                .email("domain@example.com")
                                .build()
                ));

        // when
        Author author = authorService.getById(1L);

        // then
        assertEquals(1L, author.getId());
        assertEquals("홍길동", author.getName());
        assertEquals("domain@example.com", author.getEmail());
    }

    @Test
    void update_해당_Author를_찾을_수_없으면_ApiException이_발생한다_AUTHOR_NOT_FOUND() {
        // given
        AuthorUpdate command = new AuthorUpdate(1L, "홍길동", "domain@example.com");

        given(authorRepository.findById(1L))
                .willReturn(Optional.empty());

        // when
        ApiException exception = assertThrows(
                ApiException.class, () -> authorService.update(command)
        );

        // then
        assertEquals(AUTHOR_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void update_변경사항이_없으면_아무_작업도_하지_않는다() {
        // given
        AuthorUpdate command = new AuthorUpdate(1L, "홍길동", "domain@example.com");

        Author author = Author.builder()
                .id(1L)
                .name("홍길동")
                .email("domain@example.com")
                .build();
        given(authorRepository.findById(1L))
                .willReturn(Optional.of(author));

        // when
        authorService.update(command);

        // then
        assertEquals(1L, author.getId());
        assertEquals("홍길동", author.getName());
        assertEquals("domain@example.com", author.getEmail());
    }

    @Test
    void update_이메일_변경사항이_있고_이미_사용중인_이메일인_경우_ApiException이_발생한다_ALREADY_EXIST_EMAIL() {
        // given
        AuthorUpdate command = new AuthorUpdate(1L, "홍길동", "domain@example.com");

        given(authorRepository.findById(1L))
                .willReturn(Optional.of(
                        Author.builder()
                                .id(1L)
                                .name("홍길동")
                                .email("newDomain@example.com")
                                .build()
                ));
        given(authorRepository.existsByEmail("domain@example.com"))
                .willReturn(true);

        // when
        ApiException exception = assertThrows(
                ApiException.class, () -> authorService.update(command)
        );

        // then
        assertEquals(ALREADY_EXIST_EMAIL, exception.getErrorCode());
    }

    @Test
    void update_Author의_정보를_업데이트한다() {
        // given
        AuthorUpdate command = new AuthorUpdate(1L, "철수", "newDomain@example.com");

        Author author = Author.builder()
                .id(1L).name("홍길동")
                .email("domain@example.com")
                .build();
        given(authorRepository.findById(1L))
                .willReturn(Optional.of(author));
        given(authorRepository.existsByEmail(command.email()))
                .willReturn(false);

        // when
        authorService.update(command);

        // then
        assertEquals("철수", author.getName());
        assertEquals("newDomain@example.com", author.getEmail());
    }

    @Test
    void deleteById_해당_Author를_찾을_수_없으면_ApiException이_발생한다_AUTHOR_NOT_FOUND() {
        // given
        given(authorRepository.findById(1L))
                .willReturn(Optional.empty());

        // when
        ApiException exception = assertThrows(
                ApiException.class, () -> authorService.deleteById(1L)
        );

        // then
        assertEquals(AUTHOR_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void deleteById_Author를_삭제한다() {
        // given
        Author author = Author.builder()
                .id(1L)
                .name("홍길동")
                .email("domain@example.com")
                .build();

        given(authorRepository.findById(1L))
                .willReturn(Optional.of(author));
        doNothing().when(authorBookService)
                .deleteAllBooksByAuthorId(1L);
        doNothing().when(authorRepository)
                .bulkDeleteById(author.getId());

        // when
        authorService.deleteById(1L);

        // then
    }
}