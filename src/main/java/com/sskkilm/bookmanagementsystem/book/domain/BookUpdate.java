package com.sskkilm.bookmanagementsystem.book.domain;

import java.time.LocalDate;

public record BookUpdate(
        Long id,
        String title,
        String description,
        String isbn,
        LocalDate publicationDate
) {
}
