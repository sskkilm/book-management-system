package com.sskkilm.bookmanagementsystem.book.application;

import com.sskkilm.bookmanagementsystem.book.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface BookRepository {
    boolean existsByIsbn(String isbn);

    Book save(Book book);

    Page<Book> findAllByConditionWithPaging(LocalDate publicationDate, Pageable pageable);

    Book getByIdWithAuthor(Long id);

    Book getById(Long id);

    void delete(Book book);

    void deleteAllBooksByAuthorId(Long authorId);
}
