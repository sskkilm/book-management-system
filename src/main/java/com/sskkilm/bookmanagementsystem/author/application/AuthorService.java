package com.sskkilm.bookmanagementsystem.author.application;

import com.sskkilm.bookmanagementsystem.author.domain.Author;
import com.sskkilm.bookmanagementsystem.author.domain.AuthorCreate;
import com.sskkilm.bookmanagementsystem.author.domain.AuthorUpdate;
import com.sskkilm.bookmanagementsystem.author.domain.Authors;
import com.sskkilm.bookmanagementsystem.common.application.AuthorBookService;
import com.sskkilm.bookmanagementsystem.common.error.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sskkilm.bookmanagementsystem.common.error.ErrorCode.ALREADY_EXIST_EMAIL;
import static com.sskkilm.bookmanagementsystem.common.error.ErrorCode.AUTHOR_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorBookService authorBookService;

    public Author create(AuthorCreate command) {
        if (authorRepository.existsByEmail(command.email())) {
            throw new ApiException(ALREADY_EXIST_EMAIL);
        }

        return authorRepository.save(Author.create(command));
    }

    public Authors getList() {
        return Authors.of(authorRepository.findAll());
    }

    public Author getById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ApiException(AUTHOR_NOT_FOUND));
    }

    @Transactional
    public void update(AuthorUpdate command) {
        Author author = authorRepository.findById(command.id())
                .orElseThrow(() -> new ApiException(AUTHOR_NOT_FOUND));

        if (author.hasNoChanges(command)) {
            return;
        }

        if (author.hasEmailChange(command) && authorRepository.existsByEmail(command.email())) {
            throw new ApiException(ALREADY_EXIST_EMAIL);
        }

        author.update(command);
    }

    @Transactional
    public void deleteById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ApiException(AUTHOR_NOT_FOUND));

        authorBookService.deleteAllBooksByAuthorId(author.getId());

        authorRepository.bulkDeleteById(author.getId());
    }
}
