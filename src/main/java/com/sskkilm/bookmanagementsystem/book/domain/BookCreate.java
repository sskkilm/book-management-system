package com.sskkilm.bookmanagementsystem.book.domain;

import java.time.LocalDate;

public record BookCreate(
        String title,
        String description,
        String isbn,
        LocalDate publicationDate,
        Long authorId
) {
}
