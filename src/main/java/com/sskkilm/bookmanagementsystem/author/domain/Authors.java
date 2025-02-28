package com.sskkilm.bookmanagementsystem.author.domain;

import java.util.List;

public record Authors(
        List<Author> authors
) {
    public static Authors of(List<Author> authors) {
        return new Authors(authors);
    }
}
