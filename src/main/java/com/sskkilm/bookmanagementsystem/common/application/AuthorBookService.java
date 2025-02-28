package com.sskkilm.bookmanagementsystem.common.application;

import com.sskkilm.bookmanagementsystem.author.application.AuthorRepository;
import com.sskkilm.bookmanagementsystem.author.domain.Author;
import com.sskkilm.bookmanagementsystem.book.application.BookRepository;
import com.sskkilm.bookmanagementsystem.common.error.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.sskkilm.bookmanagementsystem.common.error.ErrorCode.AUTHOR_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AuthorBookService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public Author getAuthorByAuthorId(Long authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new ApiException(AUTHOR_NOT_FOUND));
    }

    public void deleteAllBooksByAuthorId(Long authorId) {
        bookRepository.deleteAllBooksByAuthorId(authorId);
    }
}
