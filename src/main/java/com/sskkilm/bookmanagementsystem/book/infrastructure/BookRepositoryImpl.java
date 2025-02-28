package com.sskkilm.bookmanagementsystem.book.infrastructure;

import com.sskkilm.bookmanagementsystem.book.application.BookRepository;
import com.sskkilm.bookmanagementsystem.book.domain.Book;
import com.sskkilm.bookmanagementsystem.common.error.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

import static com.sskkilm.bookmanagementsystem.common.error.ErrorCode.BOOK_NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {
    private final BookJpaRepository bookJpaRepository;
    private final BookQuerydslRepository bookQuerydslRepository;

    @Override
    public boolean existsByIsbn(String isbn) {
        return bookJpaRepository.existsByIsbn(isbn);
    }

    @Override
    public Book save(Book book) {
        return bookJpaRepository.save(book);
    }

    @Override
    public Page<Book> findAllByConditionWithPaging(LocalDate publicationDate, Pageable pageable) {
        return bookQuerydslRepository.findAllByConditionWithPaging(publicationDate, pageable);
    }

    @Override
    public Book getByIdWithAuthor(Long id) {
        return bookJpaRepository.findByIdWithAuthor(id)
                .orElseThrow(() -> new ApiException(BOOK_NOT_FOUND));
    }

    @Override
    public Book getById(Long id) {
        return bookJpaRepository.findById(id)
                .orElseThrow(() -> new ApiException(BOOK_NOT_FOUND));
    }

    @Override
    public void delete(Book book) {
        bookJpaRepository.delete(book);
    }

    @Override
    public void deleteAllBooksByAuthorId(Long authorId) {
        bookJpaRepository.deleteAllBooksByAuthorId(authorId);
    }
}
