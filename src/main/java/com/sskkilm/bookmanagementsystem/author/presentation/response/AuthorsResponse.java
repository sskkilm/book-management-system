package com.sskkilm.bookmanagementsystem.author.presentation.response;

import com.sskkilm.bookmanagementsystem.author.domain.Authors;

import java.util.List;

public record AuthorsResponse(
        List<AuthorDto> authors
) {

    public static AuthorsResponse from(Authors authors) {
        return new AuthorsResponse(
                authors.authors().stream()
                        .map(AuthorDto::from)
                        .toList()
        );
    }
}
