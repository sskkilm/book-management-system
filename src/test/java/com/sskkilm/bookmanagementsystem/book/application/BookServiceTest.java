package com.sskkilm.bookmanagementsystem.book.application;

import com.sskkilm.bookmanagementsystem.author.domain.Author;
import com.sskkilm.bookmanagementsystem.book.domain.Book;
import com.sskkilm.bookmanagementsystem.book.domain.BookCreate;
import com.sskkilm.bookmanagementsystem.book.domain.BookUpdate;
import com.sskkilm.bookmanagementsystem.common.application.AuthorBookService;
import com.sskkilm.bookmanagementsystem.common.error.ApiException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;

import static com.sskkilm.bookmanagementsystem.common.error.ErrorCode.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    BookRepository bookRepository;

    @Mock
    AuthorBookService authorBookService;

    @InjectMocks
    BookService bookService;

    @Test
    void create_이미_사용중인_isbn이면_ApiException이_발생한다_ALREADY_EXIST_ISBN() {
        //given
        BookCreate command = new BookCreate(
                "오브젝트", "코드로 이해하는 객체지향 설계", "1234567890",
                LocalDate.of(2025, 2, 25), 1L
        );

        given(bookRepository.existsByIsbn("1234567890"))
                .willReturn(true);

        //when
        ApiException exception = assertThrows(
                ApiException.class, () -> bookService.create(command)
        );

        //then
        assertEquals(ALREADY_EXIST_ISBN, exception.getErrorCode());
    }

    @Test
    void create_저자가_존재하지_않으면_ApiException이_발생한다_AUTHOR_NOT_FOUND() {
        //given
        BookCreate command = new BookCreate(
                "오브젝트", "코드로 이해하는 객체지향 설계", "1234567890",
                LocalDate.of(2025, 2, 25), 1L
        );

        given(bookRepository.existsByIsbn("1234567890"))
                .willReturn(false);
        given(authorBookService.getAuthorByAuthorId(1L))
                .willThrow(new ApiException(AUTHOR_NOT_FOUND));

        //when
        ApiException exception = assertThrows(
                ApiException.class, () -> bookService.create(command)
        );

        //then
        assertEquals(AUTHOR_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void create_새로운_Book을_생성한다() {
        //given
        BookCreate command = new BookCreate(
                "오브젝트", "코드로 이해하는 객체지향 설계", "1234567890",
                LocalDate.of(2025, 2, 25), 1L
        );

        given(bookRepository.existsByIsbn("1234567890"))
                .willReturn(false);

        Author author = Author.builder()
                .id(1L)
                .name("홍길동")
                .email("domain@example.com")
                .build();
        given(authorBookService.getAuthorByAuthorId(1L))
                .willReturn(author);

        given(bookRepository.save(any(Book.class)))
                .willReturn(
                        Book.builder()
                                .id(1L)
                                .title("오브젝트")
                                .description("코드로 이해하는 객체지향 설계")
                                .isbn("1234567890")
                                .publicationDate(LocalDate.of(2025, 2, 25))
                                .author(author)
                                .build()
                );

        //when
        Book book = bookService.create(command);

        //then
        assertEquals(1L, book.getId());
        assertEquals("오브젝트", book.getTitle());
        assertEquals("코드로 이해하는 객체지향 설계", book.getDescription());
        assertEquals("1234567890", book.getIsbn());
        assertEquals(LocalDate.of(2025, 2, 25), book.getPublicationDate());
        assertEquals(1L, book.getAuthor().getId());
        assertEquals("홍길동", book.getAuthor().getName());
        assertEquals("domain@example.com", book.getAuthor().getEmail());
    }

    @Test
    void getListByConditionWithPaging_Book_페이징_결과를_반환한다() {
        //given
        LocalDate publicationDate = LocalDate.of(2025, 2, 25);
        PageRequest pageRequest = PageRequest.of(0, 10);

        Author author = Author.builder()
                .id(1L)
                .name("홍길동")
                .email("domain@example.com")
                .build();
        List<Book> bookList = List.of(
                Book.builder()
                        .id(1L)
                        .title("오브젝트")
                        .description("코드로 이해하는 객체지향 설계")
                        .isbn("1234567890")
                        .publicationDate(LocalDate.of(2025, 2, 25))
                        .author(author)
                        .build(),
                Book.builder()
                        .id(2L)
                        .title("오브젝트")
                        .description("코드로 이해하는 객체지향 설계")
                        .isbn("2234567890")
                        .publicationDate(LocalDate.of(2025, 2, 25))
                        .author(author)
                        .build()
        );
        given(bookRepository.findAllByConditionWithPaging(publicationDate, pageRequest))
                .willReturn(new PageImpl<>(bookList, PageRequest.of(0, 10), 2));

        //when
        Page<Book> books = bookService.getListByConditionWithPaging(publicationDate, pageRequest);

        //then
        assertEquals(1, books.getTotalPages());
        assertEquals(2, books.getTotalElements());

        List<Book> list = books.stream().toList();

        Book book1 = list.get(0);
        assertEquals(1L, book1.getId());
        assertEquals("오브젝트", book1.getTitle());
        assertEquals("코드로 이해하는 객체지향 설계", book1.getDescription());
        assertEquals("1234567890", book1.getIsbn());
        assertEquals(LocalDate.of(2025, 2, 25), book1.getPublicationDate());

        Book book2 = list.get(1);
        assertEquals(2L, book2.getId());
        assertEquals("오브젝트", book2.getTitle());
        assertEquals("코드로 이해하는 객체지향 설계", book2.getDescription());
        assertEquals("2234567890", book2.getIsbn());
        assertEquals(LocalDate.of(2025, 2, 25), book2.getPublicationDate());
    }

    @Test
    void getByIdWithAuthor_특정_id의_Book을_찾을_수_없으면_ApiException이_발생한다_BOOK_NOT_FOUND() {
        //given
        given(bookRepository.getByIdWithAuthor(1L))
                .willThrow(new ApiException(BOOK_NOT_FOUND));

        //when
        ApiException exception = assertThrows(
                ApiException.class, () -> bookService.getByIdWithAuthor(1L)
        );

        //then
        assertEquals(BOOK_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void getByIdWithAuthor_특정_id의_Book을_조회한다() {
        //given
        Author author = Author.builder()
                .id(1L)
                .name("홍길동")
                .email("domain@example.com")
                .build();
        given(bookRepository.getByIdWithAuthor(1L))
                .willReturn(
                        Book.builder()
                                .id(1L)
                                .title("오브젝트")
                                .description("코드로 이해하는 객체지향 설계")
                                .isbn("1234567890")
                                .publicationDate(LocalDate.of(2025, 2, 25))
                                .author(author)
                                .build()
                );

        //when
        Book book = bookService.getByIdWithAuthor(1L);

        //then
        assertEquals(1L, book.getId());
        assertEquals("오브젝트", book.getTitle());
        assertEquals("코드로 이해하는 객체지향 설계", book.getDescription());
        assertEquals("1234567890", book.getIsbn());
        assertEquals(LocalDate.of(2025, 2, 25), book.getPublicationDate());
    }

    @Test
    void update_해당_Book를_찾을_수_없으면_ApiException이_발생한다_BOOK_NOT_FOUND() {
        //given
        BookUpdate command = new BookUpdate(1L, "오브젝트2", "코드로 이해하는 객체지향 설계",
                "2234567890", LocalDate.of(2025, 2, 25)
        );

        given(bookRepository.getById(1L))
                .willThrow(new ApiException(BOOK_NOT_FOUND));

        //when
        ApiException exception = assertThrows(
                ApiException.class, () -> bookService.update(command)
        );

        //then
        assertEquals(BOOK_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void update_변경사항이_없으면_아무_작업도_하지_않는다() {
        //given
        BookUpdate command = new BookUpdate(1L, "오브젝트", "코드로 이해하는 객체지향 설계",
                "1234567890", LocalDate.of(2025, 2, 25)
        );

        Author author = Author.builder()
                .id(1L)
                .name("홍길동")
                .email("domain@example.com")
                .build();
        Book book = Book.builder()
                .id(1L)
                .title("오브젝트")
                .description("코드로 이해하는 객체지향 설계")
                .isbn("1234567890")
                .publicationDate(LocalDate.of(2025, 2, 25))
                .author(author)
                .build();
        given(bookRepository.getById(1L))
                .willReturn(book);

        //when
        bookService.update(command);

        //then
        assertEquals(1L, book.getId());
        assertEquals("오브젝트", book.getTitle());
        assertEquals("코드로 이해하는 객체지향 설계", book.getDescription());
        assertEquals("1234567890", book.getIsbn());
        assertEquals(LocalDate.of(2025, 2, 25), book.getPublicationDate());
    }

    @Test
    void update_isbn_변경사항이_있고_이미_사용중인_isbn이면_ApiException이_발생한다_ALREADY_EXIST_ISBN() {
        //given
        BookUpdate command = new BookUpdate(1L, "오브젝트", "코드로 이해하는 객체지향 설계",
                "2234567890", LocalDate.of(2025, 2, 25)
        );

        Author author = Author.builder()
                .id(1L)
                .name("홍길동")
                .email("domain@example.com")
                .build();
        given(bookRepository.getById(1L))
                .willReturn(
                        Book.builder()
                                .id(1L)
                                .title("오브젝트")
                                .description("코드로 이해하는 객체지향 설계")
                                .isbn("1234567890")
                                .publicationDate(LocalDate.of(2025, 2, 25))
                                .author(author)
                                .build()
                );
        given(bookRepository.existsByIsbn("2234567890"))
                .willReturn(true);

        //when
        ApiException exception = assertThrows(
                ApiException.class, () -> bookService.update(command)
        );

        //then
        assertEquals(ALREADY_EXIST_ISBN, exception.getErrorCode());
    }

    @Test
    void update_Book의_정보를_업데이트한다() {
        //given
        BookUpdate command = new BookUpdate(1L, "오브젝트", "코드로 이해하는 객체지향 설계",
                "2234567890", LocalDate.of(2025, 2, 25)
        );

        Author author = Author.builder()
                .id(1L)
                .name("홍길동")
                .email("domain@example.com")
                .build();
        Book book = Book.builder()
                .id(1L)
                .title("오브젝트")
                .description("코드로 이해하는 객체지향 설계")
                .isbn("1234567890")
                .publicationDate(LocalDate.of(2025, 2, 25))
                .author(author)
                .build();
        given(bookRepository.getById(1L))
                .willReturn(book);
        given(bookRepository.existsByIsbn("2234567890"))
                .willReturn(false);

        //when
        bookService.update(command);

        //then
        assertEquals(1L, book.getId());
        assertEquals("오브젝트", book.getTitle());
        assertEquals("코드로 이해하는 객체지향 설계", book.getDescription());
        assertEquals("2234567890", book.getIsbn());
        assertEquals(LocalDate.of(2025, 2, 25), book.getPublicationDate());
    }
}