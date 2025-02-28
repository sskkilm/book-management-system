package com.sskkilm.bookmanagementsystem.author.application;

import com.sskkilm.bookmanagementsystem.author.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    boolean existsByEmail(String email);

    Author save(Author author);

    List<Author> findAll();

    Optional<Author> findById(Long id);

    void bulkDeleteById(Long id);
}
