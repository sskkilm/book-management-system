package com.sskkilm.bookmanagementsystem.book.presentation.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sskkilm.bookmanagementsystem.book.domain.Book;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;

import java.util.List;

public record PageResult<T>(
        List<T> contents,
        @Schema(description = "페이지 번호", example = "1")
        int page,
        @Schema(description = "페이지 크기", example = "10")
        int size,
        @Schema(description = "총 페이지 수", example = "1")
        @JsonProperty("total_pages")
        int totalPages,
        @Schema(description = "총 요소 수", example = "2")
        @JsonProperty("total_elements")
        long totalElements
) {
    public static PageResult<BookDto> from(Page<Book> books) {
        List<BookDto> bookDtos = books.stream()
                .map(BookDto::from)
                .toList();
        return new PageResult<>(
                bookDtos, books.getNumber() + 1, books.getSize(),
                books.getTotalPages(), books.getTotalElements()
        );
    }
}
