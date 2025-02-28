package com.sskkilm.bookmanagementsystem.author.presentation.request;

import com.sskkilm.bookmanagementsystem.author.domain.AuthorUpdate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public record AuthorUpdateRequest(
        @Schema(description = "저자 이름", example = "홍길동", requiredMode = REQUIRED)
        @NotBlank(message = "저자 이름은 필수 항목입니다.")
        String name,

        @Schema(description = "이메일", example = "domain@example.com", requiredMode = REQUIRED)
        @NotBlank(message = "이메일은 필수 항목입니다.")
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        String email
) {
    public AuthorUpdate toCommand(Long id) {
        return new AuthorUpdate(id, this.name, this.email);
    }
}
