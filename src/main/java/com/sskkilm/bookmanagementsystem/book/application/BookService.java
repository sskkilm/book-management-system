package com.sskkilm.bookmanagementsystem.book.application;

import com.sskkilm.bookmanagementsystem.author.domain.Author;
import com.sskkilm.bookmanagementsystem.book.domain.Book;
import com.sskkilm.bookmanagementsystem.book.domain.BookCreate;
import com.sskkilm.bookmanagementsystem.book.domain.BookUpdate;
import com.sskkilm.bookmanagementsystem.common.application.AuthorBookService;
import com.sskkilm.bookmanagementsystem.common.error.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static com.sskkilm.bookmanagementsystem.common.error.ErrorCode.ALREADY_EXIST_ISBN;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorBookService authorBookService;

    public Book create(BookCreate command) {
        if (bookRepository.existsByIsbn(command.isbn())) {
            throw new ApiException(ALREADY_EXIST_ISBN);
        }

        Author author = authorBookService.getAuthorByAuthorId(command.authorId());

        return bookRepository.save(Book.create(command, author));
    }

    public Page<Book> getListByConditionWithPaging(LocalDate publicationDate, Pageable pageable) {
        return bookRepository.findAllByConditionWithPaging(publicationDate, pageable);
    }

    public Book getByIdWithAuthor(Long id) {
        return bookRepository.getByIdWithAuthor(id);
    }

    @Transactional
    public void update(BookUpdate command) {
        Book book = bookRepository.getById(command.id());

        if (book.hasNoChanges(command)) {
            return;
        }

        if (book.hasIsbnChange(command) && bookRepository.existsByIsbn(command.isbn())) {
            throw new ApiException(ALREADY_EXIST_ISBN);
        }

        book.update(command);
    }

    @Transactional
    public void deleteById(Long id) {
        Book book = bookRepository.getById(id);

        bookRepository.delete(book);
    }
}
