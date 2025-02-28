package com.sskkilm.bookmanagementsystem.author.presentation.response;

import com.sskkilm.bookmanagementsystem.author.domain.Author;
import io.swagger.v3.oas.annotations.media.Schema;

public record AuthorDetail(
        @Schema(description = "저자 id", example = "1")
        Long id,

        @Schema(description = "저자 이름", example = "홍길동")
        String name,

        @Schema(description = "이메일", example = "domain@example.com")
        String email
) {
    public static AuthorDetail from(Author author) {
        return new AuthorDetail(author.getId(), author.getName(), author.getEmail());
    }
}
