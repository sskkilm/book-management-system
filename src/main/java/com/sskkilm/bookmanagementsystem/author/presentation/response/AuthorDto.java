package com.sskkilm.bookmanagementsystem.author.presentation.response;

import com.sskkilm.bookmanagementsystem.author.domain.Author;
import io.swagger.v3.oas.annotations.media.Schema;

public record AuthorDto(
        @Schema(description = "저자 id", example = "1")
        Long id,

        @Schema(description = "저자 이름", example = "홍길동")
        String name
) {
    public static AuthorDto from(Author author) {
        return new AuthorDto(author.getId(), author.getName());
    }
}
