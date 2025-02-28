package com.sskkilm.bookmanagementsystem.book.presentation.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sskkilm.bookmanagementsystem.book.domain.BookCreate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

public record BookCreateRequest(
        @Schema(description = "도서 제목", example = "오브젝트", requiredMode = REQUIRED)
        @NotBlank(message = "도서 제목은 필수 항목입니다.")
        String title,

        @Schema(description = "도서 설명", example = "코드로 이해하는 객체지향 설계", requiredMode = NOT_REQUIRED)
        String description,

        @Schema(description = "국제 표준 도서번호", example = "1234567890", requiredMode = REQUIRED)
        @NotBlank(message = "국제 표준 도서번호는 필수 항목입니다.")
        @Pattern(
                regexp = "^(?:[1-9]\\d{2})(?:\\d{4,7})(?:\\d{1,3})0$",
                message = "ISBN-10 형식이 올바르지 않습니다. (예: 1234567890)"
        )
        String isbn,

        @Schema(description = "출판일", example = "2025-02-25", requiredMode = NOT_REQUIRED)
        @JsonProperty("publication_date")
        LocalDate publicationDate,

        @Schema(description = "저자 id", example = "1", requiredMode = REQUIRED)
        @NotNull(message = "저자 id는 필수 항목압니다.")
        @JsonProperty("author_id")
        Long authorId
) {
    public BookCreate toCommand() {
        return new BookCreate(title, description, isbn, publicationDate, authorId);
    }
}
