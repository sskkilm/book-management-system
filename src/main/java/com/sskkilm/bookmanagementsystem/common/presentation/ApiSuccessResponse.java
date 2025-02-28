package com.sskkilm.bookmanagementsystem.common.presentation;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.HttpStatus.CREATED;

public record ApiSuccessResponse<T>(
        @Schema(description = "응답 코드", example = "SUCCESS")
        String code,
        @Schema(description = "응답 메시지", example = "응답 메세지 입니다.")
        String message,
        T data
) {
    public static <T> ResponseEntity<ApiSuccessResponse<T>> ok(T data) {
        return ResponseEntity.ok(new ApiSuccessResponse<>("SUCCESS", null, data));
    }

    public static <T> ResponseEntity<ApiSuccessResponse<T>> created(T data) {
        return ResponseEntity.status(CREATED)
                .body(new ApiSuccessResponse<>("SUCCESS", "리소스가 생성되었습니다.", data));
    }
}
