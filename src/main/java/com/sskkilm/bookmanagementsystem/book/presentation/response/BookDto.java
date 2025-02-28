package com.sskkilm.bookmanagementsystem.book.presentation.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sskkilm.bookmanagementsystem.book.domain.Book;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "도서 응답 Dto")
public record BookDto(
        @Schema(description = "도서 id", example = "1")
        Long id,

        @Schema(description = "도서 제목", example = "오브젝트")
        String title,

        @Schema(description = "국제 표준 도서번호", example = "1234567890")
        String isbn,

        @Schema(description = "출판일", example = "2025-02-25")
        @JsonProperty("publication_date")
        LocalDate publicationDate
) {
    public static BookDto from(Book book) {
        return new BookDto(
                book.getId(),
                book.getTitle(),
                book.getIsbn(),
                book.getPublicationDate()
        );
    }
}
